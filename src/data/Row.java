package data;

public class Row {

    public Attribute[] row;
    static int rowCount = 0;
    int  rowIndex = -1;

    public Row(Attribute[] row) {
        this.row = row;
        rowIndex = rowCount;
        rowCount++;
    }

}
