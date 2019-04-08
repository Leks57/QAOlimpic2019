package sample;

import java.util.List;

public class Group {
    private static volatile Group instance;
    private List<User> users;
    private List<Mark> marks;
    private User currentUser;
    private static String[] arrayOfDays = new String[]
            {"01.03.2019", "04.03.2019", "07.03.2019", "13.03.2019", "16.03.2019",
            "19.03.2019", "22.03.2019", "25.03.2019", "28.03.2019", "31.03.2019",
            "02.04.2019", "05.04.2019", "08.04.2019", "12.04.2019", "15.04.2019",
            "17.04.2019", "20.04.2019", "23.04.2019", "26.04.2019", "29.04.2019",
            "06.05.2019", "08.05.2019", "13.05.2019", "15.05.2019", "17.05.2019",
            "21.05.2019", "23.05.2019", "25.05.2019", "27.05.2019", "30.05.2019"};

    private String participantName;
    private String participantUniversity;

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

    public static String[] getArrayOfDays() {
        return arrayOfDays;
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

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantUniversity() {
        return participantUniversity;
    }

    public void setParticipantUniversity(String participantUniversity) {
        this.participantUniversity = participantUniversity;
    }
}
