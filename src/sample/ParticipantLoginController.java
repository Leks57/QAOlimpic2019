package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ParticipantLoginController {

    @FXML
    private TextField fullName, university;

    @FXML
    private Label errorFullName, errorUniversity;

    @FXML
    public void login(ActionEvent actionEvent) {
        errorFullName.setText("");
        errorUniversity.setText("");

        if ("".equals(fullName.getText()) | "".equals(university.getText())) {
            if ("".equals(fullName.getText())) {
                errorFullName.setText("Обязательное поле!");
            }
            if ("".equals(university.getText())) {
                errorUniversity.setText("Обязательное поле!");
            }
        } else {
            Group.getInstance().setParticipantName(fullName.getText());
            Group.getInstance().setParticipantUniversity(university.getText());
            Main.changeScene(getClass().getResource("Login.fxml"));
        }
    }
}
