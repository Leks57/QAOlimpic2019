package sample;

public class Mark {

    private int value;
    private String date;
    private String student;

    public Mark(int value, String date, String student) {
        this.value = value;
        this.date = date;
        this.student = student;
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

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
