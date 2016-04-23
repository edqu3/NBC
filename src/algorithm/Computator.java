package algorithm;

import data.Attribute;

public class Computator {
	
	/**
	 * Frequency Table
	 * 
	 * 			   	    <<TARGET>>
	 * 					YES		NO	
	 * 				x	4		3		7/13
	 *<<ATTRIBUTE>>  	
	 * 				y	3		3		6/13
	 * 					7/13	6/13		
	 * 
	 * 
	 * Total = 4 + 3 + 3 + 3 = 13
	 * 
	 */
	
	public Computator(Attribute[][] attributes){
		
		
				
	}
	
	// NAIVE BAYES CLASSIFIER
	
	// Calculating the probabilities:
	// Calculating the P(X) is simply the count of X = x, where x is a class of Attribute X.
	// 	i.e. If there are 5 YES and 10 No, then P(X = YES) = 5 / (10+5)
	
	// Calculating the P(X|Y) is the probability of X = x under the state that Y = y is 
	// defined as P(X = x| Y = y) * P( Y = y ) = P( X,Y ) = P( Y = y) | X = x ) * P(X = x) 	
	// Assuming these probabilities are independent, then among all the Attributes we have  
	// P(T = t) = P(X = x) * P( Y1 = y_1 | X = x) * P( Y2 = y_2 | X = x) * ... * P( Y_n = y_n | X = x)
	// where T is our target attribute and t is its value, i.e. Play Tennis = Yes. 
	// Y is the set of all Attributes except the target. Y_i is an attribute whose values are z_j.  
	// i.e. Attribute Y_i is Outlook and its values are z { sunny, rainy, overcast } 
	// and we are given that z = sunny, given that X = Play Tennis and x = yes.
	// we get: P( Outlook = sunny | Play Tennis = Yes ).
	
	// For a new unseen set of data, whose attributes are OUTLOOK{sunny, rainy, overcast},
	// TEMPERATURE{mild, hot}, WIND{weak, strong). Target = PLAY_TENNIS{YES, NO}.
	// new data = [OUTLOOK = overcast][TEMPERATURE = mild][WIND = strong] [PLAY_TENNIS = ?]
	// using the prior probabilities calculated from the test data for PLAY_TENNIS = x and the conditions
	// given by the new data. We take the max of P(PLAY_TENNIS = YES) v.s. P(PLAY_TENNIS = NO)
	// P( PLAY_TENNIS = YES ) * P(OUTLOOK = overcast | PLAY_TENNIS = YES) * P(TEMPERATURE = mild | PLAY_TENNIS = YES ) * P(WIND = strong | PLAY_TENNIS = YES )
	// P( PLAY_TENNIS = NO ) * P(OUTLOOK = overcast | PLAY_TENNIS = NO) * P(TEMPERATURE = mild | PLAY_TENNIS = NO ) * P(WIND = strong | PLAY_TENNIS = NO )
	// and we take the max, and the new data is classified accordingly.	
	
	// CODE
	
	
	
	
}
