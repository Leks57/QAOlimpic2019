package sample;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataParser {

    public static void importCSV(String csvFile) {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            List<Mark> marks = new ArrayList<Mark>();
            while ((line = reader.readNext()) != null) {
                Mark mark = new Mark(Integer.parseInt(line[0]), line[1], line[2]);
                marks.add(mark);
            }
            Group.getInstance().setMarks(marks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportMarksToCSV(List<Mark> marks, String csvFile) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            for (Mark mark:marks) {
                String [] record = {mark.getValue().toString(), mark.getDate(), mark.getStudent()};
                writer.writeNext(record);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}