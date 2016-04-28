package data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.Map.Entry;

import algorithm.PriorGetter;

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
public class FrequencyTable implements PriorGetter{

	private static Map<String, FrequencyTable> tables 					= new HashMap<>();
	private static Map<String, BigDecimal> 	   targetClassProbabilities = new HashMap<>();
	private static boolean targetClassProbabilitiesCalculated 		    = false;
    private final static BigDecimal EPSILON = BigDecimal.ONE;
	private final static MathContext MC     = new MathContext(5,RoundingMode.HALF_UP);
    public  boolean adjustForZeroFrequency = false;					// boolean for zero frequency problem

    private BigDecimal[][] table;
	public final String tableName;
    
	private Map<String, Integer> rowMap = new HashMap<>();			// rows, values for Attribute
	private Map<String, Integer> colMap = new HashMap<>();			// columns, Class (yes, no)
	private Map<Integer, String> indexRowMap = new HashMap<>();		// maps row indexes to attributes
	private Map<Integer, String> indexColMap = new HashMap<>();		// maps column indexes to attributes
	
	private Map<String, BigDecimal> marginals = new HashMap<>();

    public FrequencyTable(String attributeName, Map<String, List<Integer>> columnComposition, Map<String, List<Integer>> targetComposition) {
    	tables.put(attributeName, this);

		// run once
		if (!targetClassProbabilitiesCalculated){
			// Calculate the P(C) target class probabilities.
			Iterator<Entry<String, List<Integer>>> iterator = targetComposition.entrySet().iterator();
			BigDecimal total = BigDecimal.ZERO;
			while (iterator.hasNext()){
				Entry<String, List<Integer>> next = iterator.next();
				total = total.add( new BigDecimal(next.getValue().size()));
			}

			iterator = targetComposition.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, List<Integer>> next = iterator.next();
				// calculate P(C)
				BigDecimal pc = new BigDecimal(next.getValue().size()).divide(total, MC);
				targetClassProbabilities.put(next.getKey(), pc);
			}
			targetClassProbabilitiesCalculated = true;
		}

		tableName = attributeName;
		
    	Set<String> rowKeys = columnComposition.keySet();			// rows, values for Attribute
    	Set<String> colKeys = targetComposition.keySet();			// columns, Class (yes, no)
    	
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
    	    	
	}
    
    public void setTableEntry(String row, String col, BigDecimal e){
    	Integer rowIndex = rowMap.get(row);
    	Integer colIndex = colMap.get(col);
    	table[rowIndex][colIndex] = e;
    }

	public static FrequencyTable getFrequencyTable(String attribute){
		return tables.get(attribute);
	}

	public void showTable(){

		System.out.println("------------------------------------------------------------------------------------------------");
		System.out.println("Frequency Table for " + this.tableName + " [adjusted for zero frequency: " + adjustForZeroFrequency + "]");
		System.out.println("------------------------------------------------------------------------------------------------");

		Iterator<String> iterator = colMap.keySet().iterator();
		String header = "";
		while (iterator.hasNext()){
			header += iterator.next() + "\t";
		}
		System.out.printf("%56s%n", header);

		Iterator<String> iterator1 = rowMap.keySet().iterator();
		while (iterator1.hasNext()){
			String row = iterator1.next();
			//TODO: create a better display of table.
//			for (int i = 0; i < table[rowMap.get(row)].length; i++) {
//				System.out.println( table[rowMap.get(row)][i] );												
//			}
			System.out.printf("%20s%40s%n", row, Arrays.toString(table[rowMap.get(row)]));
		}
		
	}

	/**
	 * Additive smoothing implementation
	 */
	public static void adjustForZeroFrequencyProblem() {
		// if any zero probability entries found, smooth counts
		boolean adjust = false;
		Iterator<Entry<String, FrequencyTable>> tableIterator = FrequencyTable.tables.entrySet().iterator();
		while (tableIterator.hasNext()){
			Entry<String, FrequencyTable> t = tableIterator.next();
			FrequencyTable fTable = t.getValue();
			BigDecimal[][] table = fTable.table;

			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[i].length; j++) {
					if (table[i][j].compareTo(BigDecimal.ZERO) == 0){
						fTable.adjustForZeroFrequency = true;
//						System.out.println("Zero probability entry found in " + fTable.tableName+
//							" Attribute under class [" + fTable.indexColMap.get(j) + "]");
						adjust = true;
					}
				}
			}
		}
		if (adjust) {
			tableIterator = FrequencyTable.tables.entrySet().iterator();
			while (tableIterator.hasNext()) {
				Entry<String, FrequencyTable> t = tableIterator.next();
				FrequencyTable fTable = t.getValue();
				BigDecimal[][] table = fTable.table;
				// Add Epsilon to all the entries in all the frequency tables.
				for (int i = 0; i < table.length; i++) {
					for (int j = 0; j < table[i].length; j++) {
						table[i][j] = table[i][j].add(EPSILON);
					}
				}
			}
		}

		tableIterator = FrequencyTable.tables.entrySet().iterator();
		while (tableIterator.hasNext()) {
			Entry<String, FrequencyTable> t = tableIterator.next();
			t.getValue().calculateProbabilities();
			t.getValue().showTable();
			System.out.println("========================END========================");
		}

	}


	private void calculateProbabilities(){
		BigDecimal dataCount = BigDecimal.ZERO;

		System.out.println("========================BEGIN========================");

		// count all instances of data
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				dataCount = dataCount.add(table[i][j]);
			}
		}

		// calculate probabilities for marginal of rows
		System.out.println("------------------------------");
		System.out.println("Row Marginals");
		BigDecimal rowSum;
		for (int i = 0; i < table.length; i++) {
			rowSum = BigDecimal.ZERO;
			for (int j = 0; j < table[i].length; j++) {
				rowSum = rowSum.add(table[i][j]);
			}
			marginals.put(indexRowMap.get(i), rowSum.divide(dataCount, MC));
			System.out.println("Marginal for " + indexRowMap.get(i) + " " + rowSum.divide(dataCount, MC));
		}

		// marginal of columns
		System.out.println("------------------------------");
		System.out.println("Column Marginals");
		BigDecimal colSum;
		for (int i = 0; i < table[0].length; i++) {
			colSum = BigDecimal.ZERO;
			for (int j = 0; j < table.length; j++) {
				colSum = colSum.add(table[j][i]);
			}
			marginals.put(indexColMap.get(i), colSum.divide(dataCount, MC));
			System.out.println("Marginal for " + indexColMap.get(i) + " " + colSum.divide(dataCount, MC));
		}

		// calculate conditional probabilities
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				table[i][j] = table[i][j].divide(marginals.get(indexColMap.get(j)).multiply(dataCount), MC);
//				System.out.println("P( " + indexRowMap.get(i) + " | " + indexColMap.get(j) + " )= " + table[i][j] );
			}
		}
	}

	public static BigDecimal getTargetClassProbability(String targetClass) {
		return targetClassProbabilities.get(targetClass);
	}

	@Override
	public BigDecimal getPriorProbability(String x, String c) {
		if (x.equals("?")){
//			System.out.println("? found");
			return BigDecimal.ONE;
		}

		Integer rowIndex = rowMap.get(x);
		Integer colIndex = colMap.get(c);
		return table[rowIndex][colIndex];	
	}
	
	@Override
	public BigDecimal getMarginalProbability(String x) {
		return marginals.get(x);
	}

}
