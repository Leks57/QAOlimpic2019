package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        CSVDataParser.importStudentsFromCSV("users.csv");
        User admin = new Admin();
        Group.getInstance().getUsers().add(admin);
        User teacher = new Teacher();
        Group.getInstance().getUsers().add(teacher);

        //Fill data of each student object with his marks
        CSVDataParser.importMarksFromCSV("marks.csv");

        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(loginParent);

        primaryStage.setTitle("QA Olimpic 2019");

        primaryStage.setScene(loginScene);
        primaryStage.show();


//        Group.getInstance().printUsers();
//        Group.getInstance().printMarks();
//        TeacherController.getMarksData().addAll(Group.getInstance().getMarks());
//        CSVDataParser.exportMarksToCSV(Group.getInstance().getMarks(),"marks.csv");
//        CSVDataParser.exportStudentsToCSV("users.csv");
    }

    public static void changeScene(URL url) {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
