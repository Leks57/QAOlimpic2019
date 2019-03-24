package sample;
import java.util.List;

public class Group {
    private static volatile Group instance;
    private List<Student> students;
    private List<User> users;
    private List<Mark> marks;
    private User currentUser;

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

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public void printMarks() {
        for (Mark mark:marks) {
            System.out.println("Mark [value=" + mark.getValue() + ", date=" + mark.getDate() + ", student=" + mark.getStudentName() + "]");
        }
    }

    public void printUsers() {
        System.out.println("The list of users:");
        for(User user:users) {
            System.out.println("Username: " + user.getUserName() + ", pass: " + user.getPass() + ", student: " + user.isStudentType());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
