package sample;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

//                for (Student student : Group.getInstance().getStudents()) {
//                    student.printMarks();
//                }
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
                //List<Student> students = new ArrayList<Student>();
                while ((line = reader.readNext()) != null) {
                    Student student = new Student(line[0], line[1], line[2], line[3], line[4]);
                    studentsAsUsers.add(student);
                    //students.add(student);
                }
                Group.getInstance().setUsers(studentsAsUsers);
                //Group.getInstance().setStudents(students);
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