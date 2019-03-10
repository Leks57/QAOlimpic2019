package sample;
import java.util.Date;

public class Mark {

    private int value;
    private Date date;
    private Student student;
    private Subject subject;

    public Mark(int value, Date date, Student student, Subject subject) {
        this.value = value;
        this.date = date;
        this.student = student;
        this.subject = subject;
    }

    public Mark(int value, Student student, Subject subject) {
        this.value = value;
        this.date = new Date();
        this.student = student;
        this.subject = subject;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
