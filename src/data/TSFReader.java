package data;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSFReader {

    private final static String SEPARATOR = "\t";

    public static Attribute[][] getData(String path, AttributeDefinition[] adc){

        Attribute[][] attributes = null;
        // get data from file
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            // returns string array of 1 element sub array String[][0]
            // [0] sub array contains unparsed row string. *1
            List<String[]> fullList = reader.readAll();

            // test if file attributes and defined attributes have same length
            String[] values = fullList.get(0)[0].split(SEPARATOR);
            if (values.length != adc.length){
                throw new Exception("Incorrect Attribute Definition Array, columns: " + values.length + " != " + "your columns (count): " + adc.length);
            }
            else{
                attributes = new Attribute[fullList.size()][adc.length];
                // add each attribute, values for first row
                for (int i = 0; i < values.length; i++) {
                    attributes[0][i] = new Attribute( adc[i], values[i]);
                }
                // add the rest
                for (int i = 1; i < fullList.size(); i++) {
                    values = fullList.get(i)[0].split(SEPARATOR);
                    for (int j = 0; j < values.length; j++) {
                        attributes[i][j] = new Attribute( adc[j], values[j]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return attributes;
    }
}
