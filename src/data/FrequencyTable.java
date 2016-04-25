package data;

import java.math.BigDecimal;
import java.util.Set;

public class FrequencyTable {

    String[] rowNames;
    String[] columnNames;
    BigDecimal[][] data;

    public FrequencyTable(int rows, Set<String> rowNames, int columns, Set<String> columnNames){
        data = new BigDecimal[rows][columns];
        this.rowNames = rowNames;
        this.columnNames = columnNames;
    }

    public void addColumn(){

    }

    public void addRow(){

    }


}
