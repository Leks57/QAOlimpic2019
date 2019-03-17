package sample;

import java.util.List;


public class Student extends User {
    private String name;
    private String surname;
    private String middleName;
    private List<Mark> marks;

    public Student (String userName, String pass, String surname, String name, String middleName) {
        super(userName, pass);
        setStudentType(true);
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

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public String getFullName() {
        return name + " " + middleName + " " + surname;
    }

}
