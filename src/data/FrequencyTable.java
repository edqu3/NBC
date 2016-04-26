package data;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class FrequencyTable {

    private static Map<String, FrequencyTable> tables = new HashMap<>();
    private final static BigDecimal EPSILON = BigDecimal.ONE;
    public static boolean adjustForZeroFrequency = false;			// boolean for zero frequency problem				

    public BigDecimal[][] table;
	public final String tableName;
    
	private Map<String, Integer> rowMap = new HashMap<>();			// rows, values for Attribute
	private Map<String, Integer> colMap = new HashMap<>();			// columns, Class (yes, no)
	private Map<Integer, String> indexRowMap = new HashMap<>();		// maps row indexes to attributes
	private Map<Integer, String> indexColMap = new HashMap<>();		// maps column indexes to attributes
	
	private Map<String, BigDecimal> marginals = new HashMap<>();

    public FrequencyTable(String attributeName, Map<String, List<Integer>> columnComposition, Map<String, List<Integer>> targetComposition) {
    	tables.put(attributeName, this);
		tableName = attributeName;
		
    	Set<String> rowKeys = columnComposition.keySet();	// rows, values for Attribute
    	Set<String> colKeys = targetComposition.keySet();	// columns, Class (yes, no)
    	
    	int rows = rowKeys.size();
    	int cols = colKeys.size();
    	
    	Iterator<String> rowIterator = rowKeys.iterator();
    	int rowIndex = 0;
    	while (rowIterator.hasNext()){
	    	String value = rowIterator.next();
	    	rowMap.put(value, rowIndex);
	    	indexRowMap.put(rowIndex, value);
	    	rowIndex++;
    	}
    	
    	Iterator<String> colIterator = colKeys.iterator();
    	int colIndex = 0;
    	while (colIterator.hasNext()){
	    	String value = colIterator.next();
	    	colMap.put(value, colIndex);
	    	indexColMap.put(colIndex, value);
	    	colIndex++;
    	}
    	
    	table = new BigDecimal[rows][cols];	// P( WIND = strong  | TARGET = yes ) is equivalent to table[strong][yes]
    	
    	System.out.println();    	
	}
    
    public void setTableEntry(String row, String col, BigDecimal e){
    	Integer rowIndex = rowMap.get(row);
    	Integer colIndex = colMap.get(col);
    	table[rowIndex][colIndex] = e;
    }

	public BigDecimal getTableEntry(String row, String col){
		//bridge key names from key set to a map and its index in this Table
		Integer rowIndex = rowMap.get(row);
		Integer colIndex = colMap.get(col);
		return table[rowIndex][colIndex];
	}

	public static FrequencyTable getFrequencyTable(String attribute){
		return tables.get(attribute);
	}

	public void showTable(){

		System.out.println("------------------------------------------------");
		System.out.println("Frequency Table for " + this.tableName + " [adjusted for zero frequency: " + adjustForZeroFrequency + "]");
		System.out.println("------------------------------------------------");

		Iterator<String> iterator = colMap.keySet().iterator();
		String header = "";
		while (iterator.hasNext()){
			header += iterator.next() + "\t";
		}
		System.out.printf("%28s%n", header);

		Iterator<String> iterator1 = rowMap.keySet().iterator();
		while (iterator1.hasNext()){
			String row = iterator1.next();
//			for (int i = 0; i < table[rowMap.get(row)].length; i++) {
//				System.out.println( table[rowMap.get(row)][i] );												
//			}
			System.out.printf("%10s%20s%n", row, Arrays.toString(table[rowMap.get(row)]));
		}
		
	}

	/**
	 * Additive smoothing implementation
	 */
	public void adjustForZeroFrequencyProblem() {
		// if the table has already been adjusted for an instance of zero probability, don't proceed.
		if (adjustForZeroFrequency == false) {
			return;			
		}
		
		// Add Epsilon to all the entries in all the frequency tables.
		Iterator<Entry<String, FrequencyTable>> iterator = tables.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String,data.FrequencyTable> entry = (Map.Entry<String,data.FrequencyTable>) iterator.next();
			FrequencyTable fTable = entry.getValue();
			
			BigDecimal[][] table = fTable.table;
			
			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[i].length; j++) {
					table[i][j] = table[i][j].add(EPSILON);
				}
			}			
			
		}		
	}

	public void calculateMarginals(){
		BigDecimal entropySum = new BigDecimal(0);
		
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				entropySum = entropySum.add(table[i][j]);
			}
		}
		
		// marginal of rows
		for (int i = 0; i < table.length; i++) {			
			BigDecimal rowSum = new BigDecimal(0);		
			for (int j = 0; j < table[i].length; j++) {
				rowSum = rowSum.add(table[i][j]);
			}
			marginals.put(indexRowMap.get(i), rowSum);
			System.out.println("Marginal for " + indexRowMap.get(i) + " " + rowSum);
		}
		
		//marginal of columns
		for (int i = 0; i < table[0].length; i++) {
			BigDecimal rowSum = new BigDecimal(0);
			rowSum = rowSum.add(table[0][i]);
			marginals.put(indexRowMap.get(i), rowSum);
			System.out.println("Marginal for " + indexColMap.get(i) + " " + rowSum);
		}
		
		
		
	}
	
}
