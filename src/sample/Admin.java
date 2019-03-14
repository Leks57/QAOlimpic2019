package sample;

public class Admin extends User {
    public Admin() {
        super("admin", "admin");
        setCanCreateStudents(true);
    }
}
