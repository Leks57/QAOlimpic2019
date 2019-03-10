package sample;
import java.util.List;
import java.util.Map;


public class Student {
    private String name;
    private String surname;
    private String middleName;
    private Map<Subject, List<Mark>> marks;

    public Student (String surname, String name, String middleName) {
        this.setSurname(surname);
        this.setName(name);
        this.setMiddleName(middleName);
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

    public Map<Subject, List<Mark>> getMarks() {
        return marks;
    }

    public void setMarks(Map<Subject, List<Mark>> marks) {
        this.marks = marks;
    }

}
