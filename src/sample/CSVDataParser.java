package sample;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVDataParser {

    public static void importMarksFromCSV(String csvFile) throws Exception {

        Path path = Paths.get(csvFile);
        if (Files.exists(path)) {
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

                //Fill data of each student object with his marks
                for (User user : Group.getInstance().getUsers()) {
                    if (user.isStudentType()) {
                        for (Mark mark : Group.getInstance().getMarks()) {
                            if (((Student)user).getFullName().equals(mark.getStudentName())) {
                                ((Student)user).getMarks().put(mark.getDate(), mark.getValue().toString());
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("File with marks doesn't exist!");
        }
    }

    public static void importStudentsFromCSV(String csvFile) throws Exception {

        Path path = Paths.get(csvFile);
        if (Files.exists(path)) {
            CSVReader reader;
            try {
                reader = new CSVReader(new FileReader(csvFile));
                String[] line;
                List<User> studentsAsUsers = new ArrayList<User>();
                while ((line = reader.readNext()) != null) {
                    Student student = new Student(line[0], line[1], line[2], line[3], line[4]);
                    studentsAsUsers.add(student);
                }
                Group.getInstance().setUsers(studentsAsUsers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("File with students doesn't exist!");
        }
    }

    public static void exportMarksToCSV(List<Mark> marks, String csvFile) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            for (Mark mark:marks) {
                String [] record = {mark.getValue().toString(), mark.getDate(), mark.getStudentName()};
                writer.writeNext(record);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportStudentsToCSV(String csvFile) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            List<User> users = Group.getInstance().getUsers();
            for (User user:users) {
                if(user.isStudentType()) {
                    String [] record = {user.getUserName(), user.getPass(), ((Student)user).getSurname(), ((Student)user).getName(), ((Student)user).getMiddleName()};
                    writer.writeNext(record);
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}