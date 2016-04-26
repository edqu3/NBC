package algorithm;

import data.*;

import java.math.BigDecimal;
import java.util.*;

public class Computator {

	private final AttributeCollection ac;

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

//	List<Attribute[][]> freqTables;

	public Computator(AttributeCollection ac){

		this.ac = ac;

		List<FrequencyTable> tables = new ArrayList<>();
		
		// get TARGET column
		int targetIndex = AttributeDefinition.getAttributeIndexFromName("TARGET");
		Column targetColumn = ac.getColumn(targetIndex);
		
		// create the frequency tables		
		for (int columnIndex = 0; columnIndex < ac.getColumnLength() - 1; columnIndex++) {
			String attributeName = AttributeDefinition.getAttributeNameFromIndex(columnIndex);
			
			Map<String, List<Integer>> columnComposition = getColumnComposition(ac.getColumn(columnIndex), columnIndex);
			Map<String, List<Integer>> targetComposition = getColumnComposition(targetColumn, targetIndex);
			
			FrequencyTable table = createFrequencyTable(attributeName, columnComposition, targetComposition);
			
			tables.add(table);			
		}

		// test
		FrequencyTable overcast = FrequencyTable.getFrequencyTable("WINDY");

		System.out.println();

	}

	public FrequencyTable createFrequencyTable(String attributeName, Map<String, List<Integer>> columnComposition, Map<String, List<Integer>> targetComposition){

		// create table
		FrequencyTable table = new FrequencyTable(attributeName, columnComposition, targetComposition);

		Iterator<Map.Entry<String, List<Integer>>> targetIterator = targetComposition.entrySet().iterator();

		boolean zeroProbabilityFound = true;						// assume there is 1 or more unseen classes *1

		while (targetIterator.hasNext()){
			Map.Entry<String, List<Integer>> targetClass = targetIterator.next();			// columns in frequency table
			Iterator<Map.Entry<String, List<Integer>>> columnIterator = columnComposition.entrySet().iterator();
			while (columnIterator.hasNext()) {
				Map.Entry<String, List<Integer>> attributeClass = columnIterator.next();    // rows in frequency table

				List<Integer> targetIndexes    = targetClass.getValue();    // "yes" class rows
				List<Integer> attributeIndexes = attributeClass.getValue();

				// TODO: Redundant iterations
				// FIXME: 4/25/2016, can be linear.
				int matches = 0;
				for (int i = 0; i < targetIndexes.size(); i++) {
					for (int j = 0; j < attributeIndexes.size(); j++) {
						Integer v1 = targetIndexes.get(i);
						Integer v2 = attributeIndexes.get(j);
						if (v1.intValue() == v2.intValue()) {
							matches++;
							zeroProbabilityFound = false;					//*1
						}
					}
				}
				if (matches == 0){
					zeroProbabilityFound = true;
					System.out.println("Zero probability entry found under class " + targetClass.getKey());
				}
				table.setTableEntry(attributeClass.getKey(), targetClass.getKey(), new BigDecimal(matches));
			}
		}

		// TODO : calculate marginals and handle unseen classes. (causes 0 probability)
		// FIXME: 4/25/2016 add 1 to each row class.
		// add 1 to all entries in table, update margins.
		if (zeroProbabilityFound){

		}

		table.showTable();

		return table;
	}



	/**
	 * returns a map where keys = class attributes and values = List containing the indexes where that class occurs.
	 * @param column
	 * @param columnIndex
     * @return
     */
	public Map<String, List<Integer>> getColumnComposition(Column column, int columnIndex){

		Attribute[] col = column.column;
		// get classes (keys) for this attribute
		String[] attributeValues = AttributeDefinition.getAttributeValues(AttributeDefinition.getAttributeNameFromIndex(columnIndex));
		// a map for each class occurrence and its indexes
		Map<String, List<Integer>> valueArrays = new HashMap<>();

		for (int i = 0; i < attributeValues.length; i++) {
			valueArrays.put(attributeValues[i], new ArrayList<Integer>() );
		}

		for (int i = 0; i < col.length; i++) {
			List<Integer> indexes = valueArrays.get(col[i].value);
			indexes.add(i);
		}

		return valueArrays;
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
	
	
	
	
}
