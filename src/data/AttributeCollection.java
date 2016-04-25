package data;

import java.util.ArrayList;
import java.util.List;

public class AttributeCollection {

    Row[] rows;
    Column[] columns;

    public Attribute[][] data;

    public AttributeCollection(Attribute[][] attributes){

        rows    = new Row[attributes.length];
        columns = new Column[attributes[0].length];

        data    = attributes;

        List<Column> cls = new ArrayList<>();

        for (int i = 0; i < attributes.length; i++) {
            rows[i] = new Row(attributes[i]);
        }

        for (int i = 0; i < attributes[0].length; i++) {
            Attribute[] a = new Attribute[attributes.length];
            for (int j = 0; j < attributes.length; j++) {
                a[j] = attributes[j][i];
            }
            cls.add( new Column(a) );
        }

        cls.toArray(this.columns);

        System.out.println();
    }

    public int getColumnLength(){
        return columns.length;
    }

    public int getRowLength(){
        return columns.length;
    }

    public Column getColumn(int i){
        return columns[i];
    }

    public Row getRow(int i){
        return rows[i];
    }
}
