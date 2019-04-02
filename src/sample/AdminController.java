package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
    private void initialize() {
        currentUser.setText("Role: admin");

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
    private AnchorPane pnl_orange, pnl_coral, pnl_yellow, pnl_green;
    @FXML
    private Button btn_journal, btn_docs, btn_classes, btn_planing, logOut, exit;

    @FXML
    private Button addStudent;
    @FXML
    private TextField userField, passField, nameField, surnameField, middlenameField;

    @FXML
    private Label currentUser;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btn_journal) {
            pnl_orange.toFront();
        } else if (event.getSource() == btn_docs) {
            pnl_coral.toFront();
        } else if (event.getSource() == btn_classes) {
            pnl_yellow.toFront();
        } else if (event.getSource() == btn_planing) {
            pnl_green.toFront();
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
            Group.getInstance().getStudents().add(student);
            usersData.add(student);
        }
    }

    private void initData() {
        for (Student student : Group.getInstance().getStudents()) {
            usersData.add(student);
        }
    }
}
