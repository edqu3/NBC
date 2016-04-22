package data;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSFReader {

    private List<String[]> fullList;
//    private RowCollection rowCollection;
    private static TSFReader ourInstance;
    
    private TSFReader(String path, AttributeDefinition[] ad) {
//        CSVReader reader;
//        try {
//            reader = new CSVReader(new FileReader(path));
//            try {
//                fullList = reader.readAll();
//                int rowCount = fullList.size();
//                for (int i = 0; i < rowCount; i++) {
//                    String rowValues = fullList.get(i)[0];
//                    rows.add(Row.buildRow(attributeList, rowValues));
//                }
//                rowCollection = new RowCollection(rows);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally{
//        	reader.close();        	
//        }
//        
    }
    
    // row rep.
//    public RowCollection getRowCollection(){
//        return this.rowCollection;
//    }
    
    // 2d array of attributes for this instance.
//    public Attribute[][] getData(){
//        
//        Attribute[][] attributes = new Attribute[rowCollection.getRowList().size()][rowCollection.getRowList().get(0).getAttributes().size()];
//        for (int i = 0; i < rowCollection.getRowList().size(); i++){
//            List<Attribute> attributes_list = rowCollection.getRowList().get(i).getAttributes();
//            for (int j = 0; j < attributes_list.size(); j++){
//                attributes[i][j] = attributes_list.get(j);
//            }
//        }
//        return attributes;
//    }

//    public static TSFReader getInstance(String path, Attribute[] attributeList) {
//        if (ourInstance == null) {
//            ourInstance = new TSFReader(path, attributeList);
//        }
//        return ourInstance;
//    }
}
