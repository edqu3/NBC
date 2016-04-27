package algorithm;

import java.math.BigDecimal;

public interface PriorGetter {
	
	/**
	 * P( X = x | C = c )
	 * @param x	- The feature value
	 * @param c	- The target class
	 * @return
	 */
	public BigDecimal getPriorProbability(String x, String c);
	
	/**
	 * P(X = x)
	 * @param x - The feature, such as P(PLAY = yes) 
	 * @return
	 */
	public BigDecimal getMarginalProbability(String x);
	

}
