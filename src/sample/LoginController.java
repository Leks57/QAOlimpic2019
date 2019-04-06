package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label output;

    @FXML
    public void login(javafx.event.ActionEvent actionEvent) {
        for (User user:Group.getInstance().getUsers()) {
            if (login.getText().equals(user.getUserName()) && password.getText().equals(user.getPass())) {
                output.setText("Login successful");
                Group.getInstance().setCurrentUser(user);
                if (login.getText().equals("admin")) {
                    Main.changeScene(getClass().getResource("Admin.fxml"));
                } else if (login.getText().equals("teacher")) {
                    Main.changeScene(getClass().getResource("Teacher.fxml"));
                } else {
                    if (user.isStudentType()) {
                        Main.changeScene(getClass().getResource("Student.fxml"));
                    }
                }
                return;
            }
        }
        output.setText("Wrong login or password!");
        output.setVisible(true);
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
}
