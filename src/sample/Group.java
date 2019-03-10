package sample;
import java.util.List;

public class Group {
    private static volatile Group instance;
    private List<Student> students;

    public static Group getInstance() {
        Group localInstance = instance;
        if (localInstance == null) {
            synchronized (Group.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Group();
                }
            }
        }
        return localInstance;
    }

    public void addStudent(Student student) {
        getInstance().getStudents().add(student);
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
