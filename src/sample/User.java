package sample;

public class User {
private String userName;
private String pass;
private boolean canCreateStudents = false;
private boolean canViewAllMarks = false;
private boolean canEditMarks = false;

public User(String userName, String pass){
    this.userName = userName;
    this.pass = pass;
}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isCanCreateStudents() {
        return canCreateStudents;
    }

    public void setCanCreateStudents(boolean canCreateStudents) {
        this.canCreateStudents = canCreateStudents;
    }

    public boolean isCanViewAllMarks() {
        return canViewAllMarks;
    }

    public void setCanViewAllMarks(boolean canViewAllMarks) {
        this.canViewAllMarks = canViewAllMarks;
    }

    public boolean isCanEditMarks() {
        return canEditMarks;
    }

    public void setCanEditMarks(boolean canEditMarks) {
        this.canEditMarks = canEditMarks;
    }
}
