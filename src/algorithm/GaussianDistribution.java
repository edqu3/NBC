package algorithm;

import helper.BigFunctions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.Attribute;
import data.Column;

public class GaussianDistribution implements PriorGetter {

	private static final MathContext MC = new MathContext(4, RoundingMode.HALF_UP);	
	private static final BigDecimal pi = new BigDecimal(3.14159);
	public static final  BigDecimal e  = new BigDecimal(2.71828);
	private static final String MISSING_STRING = "?";
	
	Map<String, BigDecimal> columnDistribution;
	String attributeName;
	
	BigDecimal mean;
	BigDecimal variance;
	BigDecimal standardDeviation;	
	
	public GaussianDistribution(Column column,  Map<String, List<Integer>> targetComposition) {
		attributeName = column.getAttributeName();		
		// calculate the mean and standard deviation for each target classes 
		
		Set<String> targetKeys = targetComposition.keySet();
		
    	Iterator<String> colIterator = targetKeys.iterator();
    	int colIndex = 0;
    	while (colIterator.hasNext()){
	    	String value = colIterator.next();
//	    	colMap.put(value, colIndex);
//	    	indexColMap.put(colIndex, value);
	    	colIndex++;
    	}
		
		mean = calculateMean(column);
		variance = calculateVariance(column);
		standardDeviation = BigFunctions.sqrt(variance, 4);						
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
	
	private BigDecimal calculateVariance(Column column){
		Attribute[] col = column.column;
		
		BigDecimal sum = BigDecimal.ZERO;	
		BigDecimal total = new BigDecimal(col.length).subtract(BigDecimal.ONE);	// n - 1
				
		for (int i = 0; i < col.length; i++) {
			// ignore missing values when calculating the mean
			String value = col[i].value;
			if (!value.equals(MISSING_STRING)) {
				BigDecimal numValue = new BigDecimal(value);					// x_i
				//square difference
				BigDecimal squaredDiff = numValue.subtract(mean).multiply(numValue.subtract(mean));	
				sum = sum.add(squaredDiff);
			}
		}		
		return sum.divide(total, MC);
	}	
	
	@Override
	public BigDecimal getPriorProbability(String x, String c) {
		// calculate the PDF
		
		// sd * sqrt( 2 * pi )		
		BigDecimal denominator = standardDeviation.multiply( 
				BigFunctions.sqrt(new BigDecimal(2).multiply(pi, MC), 4), MC);		
//		e.multiply(multiplicand)
		
		
		
		return null;
	}

	@Override
	public BigDecimal getMarginalProbability(String x) {
		// TODO Auto-generated method stub
		return null;
	}

}
