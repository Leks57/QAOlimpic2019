package sample;

import au.com.bytecode.opencsv.CSVReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("QA Olimpic 2019");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
        CSVDataParser.importCSV("marks.csv");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
