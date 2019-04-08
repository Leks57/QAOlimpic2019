package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentController {

    public static List<String> columnNames = new ArrayList<>();

    @FXML
    private TableView tableMarks;

    @FXML
    private TableView tableMyMarks;

    List<TableColumn<Map, String>> listOfColumns = new ArrayList<>();

    @FXML
    private void initialize() {
        currentUser.setText("Role: " + Group.getInstance().getCurrentUser().getUserName());
        participantName.setText(participantName.getText() + " " + Group.getInstance().getParticipantName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DecimalFormat f = new DecimalFormat("##.##");

        //get dates in months
        columnNames = Arrays.asList(Group.getInstance().getArrayOfDays());


        List<TableColumn<Map, String>> listOfEditableColumns = new ArrayList<>();

        TableColumn<Map, String> firstMonth = new TableColumn<>("Март");
        TableColumn<Map, String> secondMonth = new TableColumn<>("Апрель");
        TableColumn<Map, String> thirdMonth = new TableColumn<>("Май");

        LocalDate currentDate = LocalDate.now(); //LocalDate.parse(currentDateStr, formatter);

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
            if (date.compareTo(currentDate) <= 0) {
                listOfEditableColumns.add(tableColumn);
            }
        }
        TableColumn<Map, String> nameColumn = new TableColumn<>("Студент");
        nameColumn.setMinWidth(130);
        nameColumn.setCellValueFactory(new MapValueFactory("name"));

        TableColumn<Map, String> averageColumn = new TableColumn<>("Средняя оценка");
        averageColumn.setMinWidth(130);
        averageColumn.setCellValueFactory(new MapValueFactory("average"));

        listOfColumns.add(nameColumn);
        listOfColumns.add(firstMonth);
        listOfColumns.add(secondMonth);
        listOfColumns.add(thirdMonth);
        listOfColumns.add(averageColumn);

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

        //Set edit enable to outdated columns
        for (TableColumn tableColumn:listOfEditableColumns) {
            tableColumn.setCellFactory(cellFactoryForMap);
            tableColumn.setOnEditCommit( t -> {
                int index = ((TableColumn.CellEditEvent<Map, String>) t).getTablePosition().getRow();
                String day = ((TableColumn.CellEditEvent<Map, String>) t).getTableColumn().getText();
                if (day.length() == 1) {day = "0".concat(day);}
                String month = ((TableColumn.CellEditEvent<Map, String>) t).getTableColumn().getParentColumn().getText();
                String monthValue = null;
                if (month.equals("Март")) {
                    monthValue = "03";
                } else if (month.equals("Апрель")) {
                    monthValue = "04";
                } else if (month.equals("Май")) {
                    monthValue = "05";
                }
                String date = day.concat(".").concat(monthValue).concat(".2019");
                Map map = ((TableColumn.CellEditEvent<Map, String>) t).getTableView().getItems().get(index);
                String studentName = map.get("name").toString();
                for (User user : Group.getInstance().getUsers()) {
                    if (user.isStudentType()) {
                        if (((Student)user).getFullName().equals(studentName)) {
                            if ("".equals(((TableColumn.CellEditEvent<Map, String>) t).getNewValue())) {
                                ((Student)user).getMarks().remove(date);
                                for (Iterator<Mark> it = Group.getInstance().getMarks().iterator(); it.hasNext(); ) {
                                    Mark mark = it.next();
                                    if (studentName.equals(mark.getStudentName()) && date.equals(mark.getDate())) {
                                        it.remove();
                                    }
                                }
                            } else {
                                ((Student)user).getMarks().put(date, ((TableColumn.CellEditEvent<Map, String>) t).getNewValue());
                                Group.getInstance().getMarks().add(new Mark(Integer.parseInt(((TableColumn.CellEditEvent<Map, String>) t).getNewValue()), date, studentName));
                            }
                            Double newAvg = ((Student)user).getAverageMark();
                            map.put("average", f.format(newAvg));
                        }
                    }
                }
                map.put(date,((TableColumn.CellEditEvent<Map, String>) t).getNewValue());
                ((TableColumn.CellEditEvent<Map, String>) t).getTableView().refresh();
            });
        }

    }

    @FXML
    private AnchorPane pnl_journal, pnl_myMarks;

    @FXML
    private Button btn_journal, btn_myMarks, logOut;

    @FXML
    private Label currentUser, participantName;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btn_journal) {
            tableMarks.setItems(generateDataInMap());
            tableMarks.setEditable(false);
            tableMarks.getSelectionModel().setCellSelectionEnabled(false);
            tableMarks.getColumns().setAll(listOfColumns);

            pnl_journal.toFront();

        } else if (event.getSource() == btn_myMarks) {
            tableMyMarks.setItems(generateDataInMapForCurrentStudent());
            tableMyMarks.setEditable(true);
            tableMyMarks.getSelectionModel().setCellSelectionEnabled(true);
            tableMyMarks.getColumns().setAll(listOfColumns);

            pnl_myMarks.toFront();

        } else if (event.getSource() == logOut) {
            Main.changeScene(getClass().getResource("Login.fxml"));
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

    private ObservableList<Map> generateDataInMap() {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (User user : Group.getInstance().getUsers()) {
            if (user.isStudentType()) {
                Map<String, String> dataRow = new HashMap<>(((Student)user).getMarks());
                dataRow.put("name", ((Student)user).getFullName());
                dataRow.put("average", String.valueOf(((Student)user).getAverageMark()));
                allData.add(dataRow);
            }
        }
        return allData;
    }

    private ObservableList<Map> generateDataInMapForCurrentStudent() {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        Student student = (Student) Group.getInstance().getCurrentUser();
            Map<String, String> dataRow = new HashMap<>(student.getMarks());
            dataRow.put("name", student.getFullName());
            dataRow.put("average", String.valueOf(student.getAverageMark()));
            allData.add(dataRow);

        return allData;
    }

    @FXML
    public void handleExportStudentsMarks(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (file != null) {
            CSVDataParser.exportMarksToCSV(Group.getInstance().getMarks(), file.getPath());
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
