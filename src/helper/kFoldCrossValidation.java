package helper;

import algorithm.Classifier;
import algorithm.ProbabilityModelMaker;
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

        List<List<Attribute[]>> partitions   = partition(ac.data, k_folds);
        List<Attribute[]> 		trainingSet  = new ArrayList<>();
        List<Attribute[]> 		testingSet;
        
        BigDecimal accuracy = new BigDecimal(0);

        for (int testIndex = 0; testIndex < partitions.size(); testIndex++) {
            // build the training set.
            for (int trainingIndex = 0; trainingIndex < partitions.size(); trainingIndex++) {
                // exclude the test set.
                if (testIndex != trainingIndex){
                    trainingSet.addAll(partitions.get(trainingIndex));
                }
            }
            System.out.println("\n=============================================Cross Validation=============================================");
            System.out.println("Creating training set for test set [" + testIndex + "]");

            testingSet = partitions.get(testIndex);

            AttributeCollection newTrainingSet = AttributeCollection.listToAttributeCollection(trainingSet);
            AttributeCollection newTestingSet  = AttributeCollection.listToAttributeCollection(testingSet);

            // Make the Frequency Tables
            // TODO: Classifier accesses tables via static MAP, this object is not necessary, but it must run so the static table map is created.
            List<FrequencyTable> frequencyTables = new ProbabilityModelMaker(newTrainingSet).getTables();

            // Classify
            BigDecimal a = Classifier.classify(newTestingSet);

            accuracy = accuracy.add(a);
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            System.out.println("Accuracy for testing set " + testIndex + " is " + a);
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            // clear and move on to next fold
            trainingSet.clear();
        }
        // take average
        accuracy = accuracy.divide(new BigDecimal(partitions.size()), MC);
        System.out.println("Overall Accuracy " + accuracy);
    }

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
