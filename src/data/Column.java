package data;

public class Column {

    public Attribute[] column;
    static int columnCount = 0;
    int  columnIndex = -1;

    public Column(Attribute[] column) {
        this.column = column;
        columnIndex = columnCount;
        columnCount ++;
    }
}
