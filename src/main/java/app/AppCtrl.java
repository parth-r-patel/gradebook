package app;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by Parth on 2017-06-18.
 */
public class AppCtrl {
    @FXML private TableView<Student> recordsTable;
    @FXML private TableColumn<Student, String> nameCol;
    @FXML private TableColumn<Student, Long> projectCol = new TableColumn<>();
    @FXML private TableColumn tests = new TableColumn();
    @FXML private ArrayList<TableColumn<Student, Long>> testCols = new ArrayList<>();
    @FXML private Button add;
    @FXML private AnchorPane ap1;
    public static ObservableList<Student> tableRecords = FXCollections.observableArrayList();

    public AppCtrl() {
    }

    // TODO: use bindings for calculating averages
    @FXML
    public void initialize() {
        Student r1 = new Student();
        tableRecords.add(r1);
//        Student.addTest();
//        Student.addTest();
//
//        tableRecords.get(0).setName("letap Parth");
//        tableRecords.get(0).getAllTestScores().set(0, new SimpleLongProperty(9));
//        tableRecords.get(1).setName("letap");
//        tableRecords.get(1).getAllTestScores().set(0, new SimpleLongProperty(78));
//        tableRecords.get(1).getAllTestScores().set(1, new SimpleLongProperty(19));


        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameProp"));
        nameCol.setMinWidth(150);
        nameCol.prefWidthProperty().bind(Grbk.primaryStage.widthProperty().subtract(projectCol.widthProperty()).subtract(tests.widthProperty()).subtract(20));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                event -> tableRecords.get(event.getTablePosition().getRow()).setNameProp(event.getNewValue())
        );

        tests.setText("Tests");
        setupTestColumns();

        setupProjectColumn();

        recordsTable.getColumns().addAll(tests, projectCol);
        recordsTable.setItems(tableRecords);

        add.setOnAction(
                event -> {
                    Student.addTest();
                    createTestColumn(Student.getTestSize()-1);
                    recordsTable.refresh();
                }
        );

        AnchorPane.setRightAnchor(recordsTable, 0.5);
        AnchorPane.setLeftAnchor(recordsTable, 0.5);
    }

    private void setupTestColumns() {
        for(int i = 0; i < Student.getTestSize(); i++) {
            createTestColumn(i);
        }
    }

    private void setupProjectColumn() {
        projectCol.setText("Final Project");
        projectCol.setCellValueFactory(
                column -> new ReadOnlyObjectWrapper<>(column.getValue().getProjScoreprop())
        );

        Callback<TableColumn<Student, Long>, TableCell<Student, Long>> numericFactory = param -> new ScoreFieldTableCell();

        projectCol.setCellFactory(numericFactory);

        projectCol.setOnEditCommit(
                event -> tableRecords.get(event.getTablePosition().getRow()).setProjScoreProp(event.getNewValue())
        );
    }

    private void createTestColumn(int i) {
        TableColumn<Student, Long> test = new TableColumn<>();
        test.setPrefWidth(60);

        test.setText("Test - " + (i+1));
        test.setCellValueFactory(
                column -> new ReadOnlyObjectWrapper<>(column.getValue().getTestScoresprop(i))
        );

        Callback<TableColumn<Student, Long>, TableCell<Student, Long>> numericFactory = param -> new ScoreFieldTableCell();

        test.setCellFactory(numericFactory);

        test.setOnEditCommit(
                event -> tableRecords.get(event.getTablePosition().getRow()).setTestScoresProp(i, event.getNewValue())
        );

//        testCols.add(test);
        tests.getColumns().add(test);
    }
}
