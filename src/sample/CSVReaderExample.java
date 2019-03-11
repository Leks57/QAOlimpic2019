package sample;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReaderExample {

    public static void main(String[] args) {

        String csvFile = "marks.csv";
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println("Mark [value= " + line[0] + ", date= " + line[1] + " , student=" + line[2] + ", subject= " + line[3] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}