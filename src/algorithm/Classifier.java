package algorithm;

import data.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Classifier {

	private static final MathContext MC = new MathContext(4, RoundingMode.HALF_UP);

	public static BigDecimal classify(AttributeCollection testCollection){

		// Get TARGET variable. Assuming the decision attribute is at the end of the attribute column list
		String   targetName    = AttributeDefinition.getAttributeNameFromIndex(testCollection.getColumnLength() - 1);
		String[] targetClasses = AttributeDefinition.getAttributeValues(targetName);

		int correct = 0;
		int total = testCollection.getRowLength();

		// for each row in testing set
		for (int i = 0; i < testCollection.getRowLength(); i++) {
			Row row = testCollection.getRow(i);
			String expectedTargetClass = row.getTargetClass();
			String predictedTargetClass = "";

			// calculate probabilities
			Map<String, BigDecimal> likelihoodMap = new HashMap<>();	// necessary?

			// MAP
			BigDecimal map = BigDecimal.ZERO;

			// calculate the likelihood for under each target class.
			for (int k = 0; k < targetClasses.length; k++) {
				// get likelihood for kth class
				BigDecimal likelihood = calculateLikelihood(row, targetClasses[k]);
				likelihoodMap.put(targetClasses[k], likelihood);		// necessary?

				// if new MAP found, update
				if (likelihood.compareTo(map) == 1) {
					map = likelihood;
					predictedTargetClass = targetClasses[k];
				}
			}

			// if correctly predicted class
			if (expectedTargetClass.compareTo(predictedTargetClass) == 0){
				correct++;
			}
		}

		return new BigDecimal(correct).divide(new BigDecimal(total), MC);
	}

	// P( c | X ) = P(x_1 | c ) * P(x_2 | c ) * ... * P(x_n | c ) * P(c)
	private static BigDecimal calculateLikelihood(Row row, String targetClass){

		BigDecimal likelihood = FrequencyTable.getTargetClassProbability(targetClass);

		// iterate over attributes, except the target column
		for (int x = 0; x < row.row.length - 1; x++) {
			Attribute attribute = row.row[x];

			// get table for this attribute
			FrequencyTable frequencyTable = FrequencyTable.getFrequencyTable(attribute.name);

			// P(attribute.value | targetClass)
			BigDecimal p = frequencyTable.getTableEntry(attribute.value, targetClass);

			likelihood = likelihood.multiply(p, MC);
		}

		return likelihood;
	}
	
}
