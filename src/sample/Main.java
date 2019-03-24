package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        CSVDataParser.importStudentsFromCSV("users.csv");
        User admin = new Admin();
        Group.getInstance().getUsers().add(admin);
        User teacher = new Teacher();
        Group.getInstance().getUsers().add(teacher);

        //Fill data of each student object with his marks
        CSVDataParser.importMarksFromCSV("marks.csv");

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("QA Olimpic 2019");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();


//        Group.getInstance().printUsers();
//        Group.getInstance().printMarks();
//        Controller.getMarksData().addAll(Group.getInstance().getMarks());
//        CSVDataParser.exportMarksToCSV(Group.getInstance().getMarks(),"marks.csv");
//        CSVDataParser.exportStudentsToCSV("users.csv");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
