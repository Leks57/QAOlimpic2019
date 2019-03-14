package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("QA Olimpic 2019");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();

        User admin = new Admin();
        User teacher = new Teacher();
        List<User> users = new ArrayList<User>();
        users.add(admin);
        users.add(teacher);
        Group.getInstance().setUsers(users);

        CSVDataParser.importCSV("marks.csv");
        Group.getInstance().printMarks();
        CSVDataParser.exportMarksToCSV(Group.getInstance().getMarks(),"marks.csv");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
