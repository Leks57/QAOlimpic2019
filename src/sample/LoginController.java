package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
                }
                return;
            }
        }
        output.setText("Wrong login or password!");
        output.setVisible(true);
    }
}
