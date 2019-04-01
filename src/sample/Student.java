package sample;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Student extends User {
    private String name;
    private String surname;
    private String middleName;
    private Map<String, String> marks = new HashMap<>();

    public Student (String userName, String pass, String surname, String name, String middleName) {
        super(userName, pass);
        setStudentType(true);
        this.setSurname(surname);
        this.setName(name);
        this.setMiddleName(middleName);
        marks.put("average", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() {
        return surname + " " + name + " " + middleName;
    }

    public Map<String, String> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, String> marks) {
        this.marks = marks;
    }

    public void printMarks() {
        System.out.println("Marks of student '" + getFullName() + "':");
        for(Map.Entry<String, String> entry : marks.entrySet()) {
            System.out.println("Date: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public double getAverageMark() {
        DecimalFormat f = new DecimalFormat("##.##");
        double sum = 0;
        double avg = 0;
        for (Map.Entry<String, String> entry : marks.entrySet()) {
            if (!entry.getKey().equals("average")) {
                sum = sum + Integer.parseInt(entry.getValue());
            }
        }
        if (marks.size()-1 > 0) {
            avg = sum / (marks.size()-1);
        }
        this.marks.put("average", f.format(avg));
        return avg;
    }
}
