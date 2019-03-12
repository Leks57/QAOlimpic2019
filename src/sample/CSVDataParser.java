package sample;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVDataParser {

    public static void importCSV(String csvFile) {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println("Mark [value=" + line[0] + ", date=" + line[1] + " , student=" + line[2] + ", subject=" + line[3] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportMarksToCSV(ArrayList<Mark> marks, String csvFile) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            for (Mark mark:marks) {
                String [] record = {mark.getValue().toString(), mark.getDate().toString(), mark.getStudent().getFullName(), mark.getSubject().toString()};
                writer.writeNext(record);

                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}