package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Controller {
    private static ObservableList<Mark> marksData = FXCollections.observableArrayList();

    public static ObservableList<Mark> getMarksData() {
        return marksData;
    }

    @FXML
    private TableView<Mark> tableMarks;

    @FXML
    private TableColumn<Mark, String> userColumn;

    @FXML
    private TableColumn<Mark, String> dateColumn;

    @FXML
    private TableColumn<Mark, Integer> markColumn;

    @FXML
    private void initialize() {
        userColumn.setCellValueFactory(new PropertyValueFactory<Mark, String>("student"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Mark, String>("date"));
        markColumn.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("value"));
        tableMarks.setItems(marksData);
    }

    @FXML
    private Pane pnl_orange, pnl_coral, pnl_yellow, pnl_green;

    @FXML
    private Button btn_journal, btn_docs, btn_classes, btn_planing;

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
        }
    }
}
