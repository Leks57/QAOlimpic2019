package sample;

public class Teacher extends User {
    public Teacher() {
        super("teacher", "teacher");
        setCanEditMarks(true);
        setCanViewAllMarks(true);
    }
}
