package data;

import data.AttributeDefinition.TYPE;

public class Column {

    public Attribute[] column;
    static int columnCount = 0;
    int  columnIndex = -1;    
    
    public Column(Attribute[] column) {
        this.column = column;
        columnIndex = columnCount;
        columnCount ++;
    }
    
    public String getAttributeName(){
    	return column[0].name;
    }
    
    public TYPE getDataType(){
    	return column[0].definition.attributeType();
    }
}
