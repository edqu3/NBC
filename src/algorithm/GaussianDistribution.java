package algorithm;

import helper.BigFunctions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import data.Attribute;
import data.Column;

public class GaussianDistribution implements PriorGetter {

	private static Map<String, GaussianDistribution> gDistributions = new HashMap<>();
	
	private static final MathContext MC = new MathContext(4, RoundingMode.HALF_UP);	
	private static final BigDecimal pi = new BigDecimal(3.14159);	
	private static final String MISSING_STRING = "?";
		
	// map for target classes and their probability
	// p(t = c1) + p(t = c2) + ... + p(t = cn) = 1
	private Map<String, BigDecimal> columnDistribution = new HashMap<>();
	private Map<String, BigDecimal> targetMeans = new HashMap<>();
	private Map<String, BigDecimal> targetStandardDeviations = new HashMap<>();
	
	String attributeName;

	public GaussianDistribution(Column columnAttribute, Map<String, List<Integer>> targetComposition) {
		attributeName = columnAttribute.getAttributeName();		
		gDistributions.put(attributeName, this);
 		
		/// Calculate target class probabilities
		
		Iterator<Entry<String, List<Integer>>> iterator = targetComposition.entrySet().iterator();
		BigDecimal instances = BigDecimal.ZERO;		
		// count all instances of classes
		while (iterator.hasNext()) {
			Entry<String, List<Integer>> next = iterator.next();
			instances = instances.add(new BigDecimal(next.getValue().size()));									
		}		
		// calculate probabilities for target classes
		iterator = targetComposition.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<Integer>> next = iterator.next();
//			columnDistribution.put(next.getKey(), new BigDecimal(next.getValue().size()).divide(instances, MC));	// marginal p
			columnDistribution.put(next.getKey(), new BigDecimal(next.getValue().size()));
		}
		
		
		/// calculate the mean and standard deviation for each target classes
		iterator = targetComposition.entrySet().iterator();
		// iterate over all target classes
		while (iterator.hasNext()) {
			Entry<String, List<Integer>> next = iterator.next();
			// list of all indexes under target class c
			List<Integer>   indexes 		   = next.getValue();
			List<Attribute> matchingAttributes = new ArrayList<>();
			for (int i = 0; i < indexes.size(); i++) {
				// get index where target class is c
				Integer index = indexes.get(i);
				Attribute matchingAttribute = columnAttribute.column[index];
				matchingAttributes.add(matchingAttribute);
			}
			// preprocess data
			Attribute[] ma = new Attribute[matchingAttributes.size()];
			for (int i = 0; i < matchingAttributes.size(); i++) {
				ma[i] = matchingAttributes.get(i);								
			}
			Column subColumn = new Column(ma);
			
			// calculate MEAN,VAR,SD for these matching attributes
			targetMeans.put(next.getKey(), calculateMean(subColumn));
			BigDecimal variance = calculateVariance(subColumn, next.getKey());
			if (variance.compareTo(BigDecimal.ZERO) == 0){
				targetStandardDeviations.put(next.getKey(), BigDecimal.ZERO);

			}else{
				targetStandardDeviations.put(next.getKey(), BigFunctions.sqrt(variance,4));
			}
		}
	}
	
	public BigDecimal calculateMean(Column column){
		
		Attribute[] col = column.column;
		
		BigDecimal sum = BigDecimal.ZERO;		
		BigDecimal total = new BigDecimal(col.length);		
		
		for (int i = 0; i < col.length; i++) {
			// ignore missing values when calculating the mean
			String value = col[i].value;
			if (!value.equals(MISSING_STRING)) {
				BigDecimal numValue = new BigDecimal(value);
				sum = sum.add(numValue);
			}
		}		
		
		return sum.divide(total, MC);
	}
	
	private BigDecimal calculateVariance(Column column, String targetClass){
		Attribute[] col = column.column;
		
		BigDecimal sum = BigDecimal.ZERO;	
		BigDecimal total = new BigDecimal(col.length).subtract(BigDecimal.ONE);	// n - 1
				
		for (int i = 0; i < col.length; i++) {
			// ignore missing values when calculating the mean
			String value = col[i].value;
			if (!value.equals(MISSING_STRING)) {
				BigDecimal numValue = new BigDecimal(value);					// x_i
				BigDecimal mean = targetMeans.get(targetClass);
				//square difference
				BigDecimal squaredDiff = numValue.subtract(mean).multiply(numValue.subtract(mean));
				sum = sum.add(squaredDiff);
			}
		}		
		return sum.divide(total, MC);
	}	
	
	public static GaussianDistribution getGaussianDistribution(String attribute){
		return gDistributions.get(attribute);
	}
	
	// x = attribute column name, c = target class name
	@Override
	public BigDecimal getPriorProbability(String x, String c) {
		// When the mean or variance is 0. return 1 so we don't end up with a 0 probability in the chain.
		// Since this attribute does not contribute to the prior, we can safely ignore it.
		if ( targetMeans.get(c).compareTo(BigDecimal.ZERO) == 0 || targetStandardDeviations.get(c).compareTo(BigDecimal.ZERO) == 0){
			return BigDecimal.ONE;
		}
		/// calculate the PDF using
		/// [1 / (sd * sqrt( 2 * pi ))] * e ^ [-1 * ( (x - mean)^2 / (2*variance) ]   
		
		// sd * sqrt( 2 * pi )
		BigDecimal standardDeviation = targetStandardDeviations.get(c);
		BigDecimal denominator = standardDeviation.multiply( 
				BigFunctions.sqrt(new BigDecimal(2).multiply(pi, MC), 4), MC);		
		// (x - mean)^2
		// TODO: get the value for x
		BigDecimal attributeValue = new BigDecimal(x);
		BigDecimal mean = targetMeans.get(c);
		BigDecimal sqDiff = attributeValue.subtract(mean).multiply(attributeValue.subtract(mean), MC);
		// 2 * variance 
		BigDecimal variance = targetStandardDeviations.get(c).multiply(targetStandardDeviations.get(c),MC);
		BigDecimal dubVariance = variance.multiply(new BigDecimal(2));
		// [-1 * ( (x - mean)^2 / (2*variance) ]
		BigDecimal exponent = sqDiff.divide(dubVariance,MC).negate();
		// e ^ [-1 * ( (x - mean)^2 / (2*variance)
		BigDecimal numerator = BigFunctions.exp(exponent, 4);
		
		// prior
		return numerator.divide(denominator, MC);			
	}

	@Override
	public BigDecimal getMarginalProbability(String x) {
		// use columnDistribution map 
		return null;
	}

	public void display() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Created Gaussian Distribution for " + attributeName);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		Iterator<Entry<String, BigDecimal>> iterator = targetMeans.entrySet().iterator();
		Iterator<Entry<String, BigDecimal>> iterator1 = targetStandardDeviations.entrySet().iterator();
		while (iterator.hasNext()){
			Entry<String, BigDecimal> next  = iterator.next();
			Entry<String, BigDecimal> next1 = iterator1.next();

			System.out.println("Target Class Means: " + next.getKey() + " = " + next.getValue() + ", " +  next1.getValue() );

		}

	}
}
