package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AdminController {

    private ObservableList<Student> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView tableStudents;

    @FXML
    private TableColumn<Student, String> userColumn;

    @FXML
    private TableColumn<Student, String> passColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> surnameColumn;

    @FXML
    private TableColumn<Student, String> middlenameColumn;

    @FXML
    Button save;

    @FXML
    private Label currentUser, participantName;

    @FXML
    private void initialize() {
        currentUser.setText("Role: admin");
        participantName.setText(participantName.getText() + " " + Group.getInstance().getParticipantName());

        initData();
        userColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("userName"));
        passColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("pass"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        middlenameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("middleName"));

        tableStudents.setItems(usersData);
    }
//
    @FXML
    private AnchorPane pnl_users;
    @FXML
    private Button btn_users, logOut;

    @FXML
    private TextField userField, passField, nameField, surnameField, middlenameField;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btn_users) {
            pnl_users.toFront();
        } else if (event.getSource() == logOut) {
            Main.changeScene(getClass().getResource("Login.fxml"));
        }
    }

    @FXML
    private void addStudentAction(ActionEvent event) {
        if (userField.getText().isEmpty() && passField.getText().isEmpty() && nameField.getText().isEmpty() &&
                surnameField.getText().isEmpty() && middlenameField.getText().isEmpty()) {
        } else {
            Student student = new Student(userField.getText(), passField.getText(), surnameField.getText(),
                    nameField.getText(), middlenameField.getText());
            Group.getInstance().getUsers().add(student);
            usersData.add(student);
            userField.setText("");
            passField.setText("");
            nameField.setText("");
            surnameField.setText("");
            middlenameField.setText("");
        }
    }

    @FXML
    private void handleReportBug(ActionEvent event) {

        final Stage dialog = new Stage();
        Parent pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("BugReport.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        dialog.setScene(scene);
        dialog.setResizable(false);
        dialog.show();
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        CSVDataParser.exportStudentsToCSV("users.csv");
    }

    private void initData() {
        for (User user : Group.getInstance().getUsers()) {
            if (user.isStudentType()) {
                usersData.add((Student)user);
            }
        }
    }

    @FXML
    private void handleExportStudents(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (file != null) {
            CSVDataParser.exportStudentsToCSV(file.getPath());
        }
    }

    @FXML
    private void handleShowInfo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Как отправить отчет об ошибке");
        alert.setHeaderText(null);
        alert.setContentText("1. Нажмите кнопку \"Отчет об ошибке\" в левом верхнем углу приложения\n" +
                "2. Опишите подробно обнаруженную ошибку\n" +
                "3. Для прикрепления скриншота поставьте галочку \"Приложить скриншот\"\n" +
                "4. Нажмите кнопку \"Отправить отчет\"");
        alert.showAndWait();
    }
}
