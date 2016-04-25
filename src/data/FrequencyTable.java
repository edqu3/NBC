package data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FrequencyTable {

    public BigDecimal[][] table;
    
    public static Map<String, FrequencyTable> tables = new HashMap<String, FrequencyTable>();
    
	Map<String, Integer> rowMap = new HashMap<>();			// rows, values for Attribute
	Map<String, Integer> colMap = new HashMap<>();			// columns, Class (yes, no)

    public FrequencyTable(String attributeName, Map<String, List<Integer>> columnComposition, Map<String, List<Integer>> targetComposition) {
    	tables.put(attributeName, this);
    	
    	Set<String> rowKeys = columnComposition.keySet();	// rows, values for Attribute
    	Set<String> colKeys = targetComposition.keySet();	// columns, Class (yes, no)
    	
    	int rows = rowKeys.size();
    	int cols = colKeys.size();
    	
    	Iterator<String> rowIterator = rowKeys.iterator();
    	int rowIndex = 0;
    	while (rowIterator.hasNext()){
	    	String value = rowIterator.next();
	    	rowMap.put(value, rowIndex);
	    	rowIterator.remove();
	    	rowIndex++;
    	}
    	
    	Iterator<String> colIterator = colKeys.iterator();
    	int colIndex = 0;
    	while (colIterator.hasNext()){
	    	String value = colIterator.next();
	    	colMap.put(value, colIndex);
	    	colIterator.remove();
	    	colIndex++;
    	}
    	
    	table = new BigDecimal[rows][cols];	// P( WIND = strong  | TARGET = yes ) is equivalent to table[strong][yes]
    	
    	//bridge key names from key set to a map and its index in this Table        	
    	
    	System.out.println();
    	
	}
    
    public void setTableEntry(int row, int col){
    	    	    	    	    	
    }
    
    public void setTableEntry(String row, String col, BigDecimal o){
    	Integer rowIndex = rowMap.get(row);
    	Integer colIndex = colMap.get(col);
    	table[rowIndex][colIndex] = o;    	
    }
    
    private int getKeyIndex(String key){
    	return 0;    	    	
    }
    
    public void addColumn(String key, List<Attribute> col){
    	int keyIndex = getKeyIndex(key);
    	
    	// key = decision and attributes are the values
    	for (int i = 0; i < col.size(); i++) {
//    		data[keyIndex][i] = col.get(i).value;
    		col.get(i);
			
		}
    	
    	
    }

    public void addRow(){

    }


}
