package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private static String marksPath = "marks.csv";
    private static String usersPath = "users.csv";

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        CSVDataParser.importStudentsFromCSV(getUsersPath());
        User admin = new Admin();
        Group.getInstance().getUsers().add(admin);
        User teacher = new Teacher();
        Group.getInstance().getUsers().add(teacher);

        //Fill data of each student object with his marks
        CSVDataParser.importMarksFromCSV(getMarksPath());

        Parent loginParent = FXMLLoader.load(getClass().getResource("ParticipantLogin.fxml"));
        Scene loginScene = new Scene(loginParent);

        primaryStage.setTitle("QA Olimpic 2019");

        primaryStage.setResizable(false);
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public static void changeScene(URL url) {
        Parent pane = null;
        try {
            pane = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static String getMarksPath() {
        return marksPath;
    }

    public static String getUsersPath() {
        return usersPath;
    }
}
