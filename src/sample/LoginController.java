package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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


    @FXML
    private void handleReportBug(ActionEvent event) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.getPrimaryStage());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Describe the bug:"));
        TextArea textArea = new TextArea();
        textArea.setPrefSize(150, 150);
        Button createReport = new Button("Create report");
        Button cancelReport = new Button("Cancel");
        dialogVbox.getChildren().add(textArea);
        dialogVbox.getChildren().add(createReport);
        dialogVbox.getChildren().add(cancelReport);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
