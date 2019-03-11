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

        String csvFile = "marks.csv";
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println("Mark [value= " + line[0] + ", date= " + line[1] + " , student=" + line[2] + ", subject= " + line[3] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
