package helper;

import algorithm.Classifier;
import algorithm.FrequencyTableMaker;
import data.Attribute;
import data.AttributeCollection;
import data.FrequencyTable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class kFoldCrossValidation {
    private static final MathContext MC = new MathContext(2, RoundingMode.HALF_UP );

    private kFoldCrossValidation(){}
    /**
     * Train on n-k rows of data, test on k where k = rows/folds
     * A new tree must be created for each validation
     * */
    public static void crossValidate(AttributeCollection ac, int k_folds){

        List<List<Attribute[]>> partitions = partition(ac.data, k_folds);
        List<Attribute[]> trainingSet  = new ArrayList<>();
        List<Attribute[]> testingSet   = new ArrayList<>();
        BigDecimal accuracy = new BigDecimal(0);

        for (int testIndex = 0; testIndex < partitions.size(); testIndex++) {
            // build the training set.
            for (int trainingIndex = 0; trainingIndex < partitions.size(); trainingIndex++) {
                // exclude the test set.
                if (testIndex != trainingIndex){
                    trainingSet.addAll(partitions.get(trainingIndex));
                }
            }
            System.out.println("\n===============Cross Validation===============");
            System.out.println("Creating training set for test set [" + testIndex + "]");

            testingSet = partitions.get(testIndex);

            AttributeCollection newTrainingSet = AttributeCollection.listToAttributeCollection(trainingSet);
            AttributeCollection newTestingSet  = AttributeCollection.listToAttributeCollection(testingSet);

            // Make the Frequency Tables
            // TODO: Classifier accesses tables via static MAP, this object is not necessary, but it must run so the static table map is created.
            List<FrequencyTable> frequencyTables = new FrequencyTableMaker(newTrainingSet).getTables();

            // Classify
            BigDecimal a = Classifier.classify(newTestingSet);

            accuracy = accuracy.add(a);
            System.out.println("-------------------------------------------------------");
            System.out.println("Accuracy for testing set " + testIndex + " is " + a);
            System.out.println("-------------------------------------------------------");
            // clear and move on to next fold
            trainingSet.clear();
        }
        accuracy = accuracy.divide(new BigDecimal(partitions.size()), MC);
        System.out.println("Overall Accuracy " + accuracy);
    }

    /**
     * Compare each TARGET from the test set against the Decision Tree.
     * returns the percentage of correct inferences.
     * */
//    private static BigDecimal validate(AttributeCollection test_set, DTNode tree, String target){
//        Attribute[][] rows = test_set.collection;
//        DTNode original_tree = tree;
//        BigDecimal correct = BigDecimal.ZERO;
//        int targetIndex = test_set.getAttributeIndex(target);
//        for(Attribute[] row : rows){
//            correct = correct.add(accuracy(tree, row, test_set, targetIndex));
//            tree = original_tree;
//        }
//        return correct.divide(new BigDecimal(rows.length), MC);
//    }

//    private static BigDecimal accuracy(DTNode tree, Attribute[] row, AttributeCollection test_set, int targetIndex){
//        if (tree.isValue){
//            // if at a leaf get its decision
//            if (tree.isLeaf){
//                // get decision, assuming there is only 1 branch, the decision.
//                String treeDecision = tree.name;
//                String testDecision = row[targetIndex].value;
//                if( treeDecision.equals(testDecision) ){
//                    // inferred decision and test data match
//                    System.out.println("Match " + testDecision);
//                    return BigDecimal.ONE;
//                }
//                else{
//                    System.out.println("No Match " + testDecision);
//                    return BigDecimal.ZERO;
//                }
//            }
//            else{
//                // move on to next node ( an Attribute )
//                tree = tree.branches.get(0);
//                return accuracy(tree, row, test_set, targetIndex );
//
//            }
//
//        }
//        else if (tree.isAttribute){
//            // get values for attribute
//            List<DTNode> branches = tree.branches;
//            String testNodeValue = row[test_set.getAttributeIndex(tree.name)].value;
//            boolean found = false;
//            for(DTNode branch : branches){
//                if(branch.name.equals(testNodeValue)){
//                    return accuracy(branch, row, test_set, targetIndex );
//                }
//            }
//            if (found == false){
//                return BigDecimal.ZERO;
//            }
//        }
//        System.out.println();
//        return null;
//    }

    private static List<List<Attribute[]>> partition(Attribute[][] original_list, int k_folds){
        int foldSize = (int) Math.ceil((double)original_list.length / k_folds);

        List<Attribute[]> attributes = Arrays.asList(original_list);
        List<List<Attribute[]>> partitions = new LinkedList<>();

        for (int i = 0; i < original_list.length; i += foldSize) {
            partitions.add(attributes.subList(i, Math.min(i + foldSize, original_list.length)));
        }
        return partitions;
    }

}