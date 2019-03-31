package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class teacherController {

    public static List<String> columnNames = new ArrayList<>();


    @FXML
    private TableView tableMarks;

    @FXML
    private void initialize() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String[] arrayOfDays = new String[] {"01.03.2019", "02.03.2019", "03.03.2019", "04.03.2019", "05.03.2019", "06.03.2019", "07.03.2019", "08.03.2019", "09.03.2019", "10.03.2019",
                "01.04.2019", "02.04.2019", "03.04.2019", "04.04.2019", "05.04.2019", "06.04.2019", "07.04.2019", "08.04.2019", "09.04.2019", "10.04.2019",
                "01.05.2019", "02.05.2019", "03.05.2019", "04.05.2019", "05.05.2019", "06.05.2019", "07.05.2019", "08.05.2019", "09.05.2019", "10.05.2019"};
        columnNames = Arrays.asList(arrayOfDays);

        List<TableColumn<Map, String>> listOfColumns = new ArrayList<>();

        TableColumn<Map, String> firstMonth = new TableColumn<>("March");
        TableColumn<Map, String> secondMonth = new TableColumn<>("April");
        TableColumn<Map, String> thirdMonth = new TableColumn<>("May");
        for (String column:columnNames) {
            LocalDate date = LocalDate.parse(column, formatter);
            String day = String.valueOf(date.getDayOfMonth());
            TableColumn<Map, String> tableColumn = new TableColumn<>(day);
            //tableColumn.setMinWidth(70);
            tableColumn.setCellValueFactory(new MapValueFactory(column));
            int month = date.getMonth().getValue();
            if(month == 3){
                firstMonth.getColumns().add(tableColumn);
            } else if (month == 4) {
                secondMonth.getColumns().add(tableColumn);
            } else if (month == 5) {
                thirdMonth.getColumns().add(tableColumn);
            }
        }
        TableColumn<Map, String> nameColumn = new TableColumn<>("name");
        nameColumn.setMinWidth(130);
        nameColumn.setCellValueFactory(new MapValueFactory("name"));

        listOfColumns.add(nameColumn);
        listOfColumns.add(firstMonth);
        listOfColumns.add(secondMonth);
        listOfColumns.add(thirdMonth);

        tableMarks.setItems(generateDataInMap());

        tableMarks.setEditable(true);
        tableMarks.getSelectionModel().setCellSelectionEnabled(true);

        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = new Callback<TableColumn<Map, String>,
                TableCell<Map, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        if (t == null) {
                            return null;
                        }
                        return t.toString();
                    }
                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });
            }
        };

        for (TableColumn tableColumn:listOfColumns) {
            tableColumn.setCellFactory(cellFactoryForMap);
        }

        //Possibility to edit columns
        for (TableColumn tableColumn:secondMonth.getColumns()) {
            tableColumn.setCellFactory(cellFactoryForMap);
        }

        secondMonth.getColumns().get(1).setOnEditCommit( t -> {
            int index = ((TableColumn.CellEditEvent<Map, String>) t).getTablePosition().getRow();
            String date = ((TableColumn.CellEditEvent<Map, String>) t).getTableColumn().getText();
            Map map = ((TableColumn.CellEditEvent<Map, String>) t).getTableView().getItems().get(index);
            map.put("06.04.2019",((TableColumn.CellEditEvent<Map, String>) t).getNewValue());
            ((TableColumn.CellEditEvent<Map, String>) t).getTableView().refresh();
        });

        tableMarks.getColumns().setAll(listOfColumns);
    }

    @FXML
    private AnchorPane pnl_orange, pnl_coral, pnl_yellow, pnl_green;

    @FXML
    private Button btn_journal, btn_docs, btn_classes, btn_planing, logOut, exit;

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
            Main.changeScene(getClass().getResource("login.fxml"));
        }
    }

    private ObservableList<Map> generateDataInMap() {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (Student student:Group.getInstance().getStudents()) {
            Map<String, String> dataRow = new HashMap<>(student.getMarks());
            dataRow.put("name", student.getFullName());
            allData.add(dataRow);
        }
        return allData;
    }
}
