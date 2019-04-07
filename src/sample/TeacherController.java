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

public class TeacherController {

    public static List<String> columnNames = new ArrayList<>();


    @FXML
    private TableView tableMarks;

    @FXML
    private void initialize() {
        currentUser.setText("Role: teacher");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DecimalFormat f = new DecimalFormat("##.##");

        String[] arrayOfDays = new String[] {"01.03.2019", "02.03.2019", "03.03.2019", "04.03.2019", "05.03.2019", "06.03.2019", "07.03.2019", "08.03.2019", "09.03.2019", "10.03.2019",
                "01.04.2019", "02.04.2019", "03.04.2019", "04.04.2019", "05.04.2019", "06.04.2019", "07.04.2019", "08.04.2019", "09.04.2019", "10.04.2019",
                "01.05.2019", "02.05.2019", "03.05.2019", "04.05.2019", "05.05.2019", "06.05.2019", "07.05.2019", "08.05.2019", "09.05.2019", "10.05.2019"};
        columnNames = Arrays.asList(arrayOfDays);

        List<TableColumn<Map, String>> listOfColumns = new ArrayList<>();

        List<TableColumn<Map, String>> listOfEditableColumns = new ArrayList<>();

        TableColumn<Map, String> firstMonth = new TableColumn<>("Март");
        TableColumn<Map, String> secondMonth = new TableColumn<>("Апрель");
        TableColumn<Map, String> thirdMonth = new TableColumn<>("Май");

        String currentDateStr = "03.04.2019";
        LocalDate currentDate = LocalDate.parse(currentDateStr, formatter);

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
                                for (Mark mark : Group.getInstance().getMarks()) {
                                    if (studentName.equals(mark.getStudentName()) && date.equals(mark.getDate())) {
                                        Group.getInstance().getMarks().remove(mark);
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

        tableMarks.getColumns().setAll(listOfColumns);
    }

    @FXML
    private AnchorPane pnl_journal;

    @FXML
    private Button btn_journal, logOut, exit;

    @FXML
    private Label currentUser;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btn_journal) {
            pnl_journal.toFront();
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
    public void saveButtonAction(ActionEvent event) {
        CSVDataParser.exportMarksToCSV(Group.getInstance().getMarks(), Main.getMarksPath());
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
