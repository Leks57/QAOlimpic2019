package sample;

public class Mark {

    private int value;
    private String date;
    private String studentName;

    public Mark(int value, String date, String studentName) {
        this.value = value;
        this.date = date;
        this.studentName = studentName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudent(String studentName) {
        this.studentName = studentName;
    }
}
