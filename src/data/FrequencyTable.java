package data;

import java.math.BigDecimal;
import java.util.*;

public class FrequencyTable {

    public BigDecimal[][] table;
	public final String tableName;

    private static Map<String, FrequencyTable> tables = new HashMap<>();
    
	Map<String, Integer> rowMap = new HashMap<>();			// rows, values for Attribute
	Map<String, Integer> colMap = new HashMap<>();			// columns, Class (yes, no)

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
	    	rowIndex++;
    	}
    	
    	Iterator<String> colIterator = colKeys.iterator();
    	int colIndex = 0;
    	while (colIterator.hasNext()){
	    	String value = colIterator.next();
	    	colMap.put(value, colIndex);
	    	colIndex++;
    	}
    	
    	table = new BigDecimal[rows][cols];	// P( WIND = strong  | TARGET = yes ) is equivalent to table[strong][yes]
    	
    	//bridge key names from key set to a map and its index in this Table        	
    	
    	System.out.println();
    	
	}
    
    public void setTableEntry(String row, String col, BigDecimal e){
    	Integer rowIndex = rowMap.get(row);
    	Integer colIndex = colMap.get(col);
    	table[rowIndex][colIndex] = e;
    }

	public BigDecimal getTableEntry(String row, String col){
		Integer rowIndex = rowMap.get(row);
		Integer colIndex = colMap.get(col);
		return table[rowIndex][colIndex];
	}

	public static FrequencyTable getFrequencyTable(String attribute){
		return tables.get(attribute);
	}

	public void showTable(){

		System.out.println("------------------------------------------------");
		System.out.println("Frequency Table for " + this.tableName);
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
			System.out.printf("%10s%20s%n", row, Arrays.toString(table[rowMap.get(row)]));
		}


	}

}
