package app;

import custom_elements.ScoreFieldTableCell;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleLongProperty;
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
    @FXML private TableColumn testsCol = new TableColumn();
    @FXML private ArrayList<TableColumn<Student, Long>> testCols = new ArrayList<>();
    @FXML private Button add;
    @FXML private Button addStudent;
    @FXML private AnchorPane ap1;
    @FXML private TabPane tabs;
    static ObservableList<Student> tableRecords;

    public AppCtrl() {
    }

    // TODO: use bindings for calculating averages
    @FXML
    public void initialize() {
//        AnchorPane.setRightAnchor(recordsTable, 0.5);
//        AnchorPane.setLeftAnchor(recordsTable, 0.5);

//        initializeTableRecords();

//        setupNameColumn();
//        setupTestColumns();
//        setupProjectColumn();
//        setupButtonHandlers();
//
//        recordsTable.getColumns().addAll(testsCol, projectCol);
//        recordsTable.setItems(tableRecords);
    }

    private void initializeTableRecords() {
        tableRecords = FXCollections.observableArrayList(Storage.students);

        // ADD a single sample student to table if course database is empty
        // TODO: Dynamic nested column headers are now visable in JavaFX 9
        if(tableRecords.size() == 0) {
            Student s = new Student("Sample Student");
            s.getAllTestScores().add(new SimpleLongProperty(-1));
            Student.testSize++;
            tableRecords.add(s);
        }
    }

    private void setupButtonHandlers() {
        add.setOnAction((event) -> {
            Student.addTest();
            createTestColumn(Student.testSize-1);
            recordsTable.refresh();
        });

        addStudent.setOnAction((event) -> {
            Student s = new Student("Sample Student");
            for(int i = 0; i < Student.testSize; i++) {
                s.getAllTestScores().add(new SimpleLongProperty(-1));
            }
            tableRecords.add(s);
        });
    }

    private void setupNameColumn() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameProp"));
        nameCol.setMinWidth(150);
        nameCol.setResizable(false);
        nameCol.prefWidthProperty().bind(Grbk.primaryStage.widthProperty().subtract(projectCol.widthProperty()).subtract(testsCol.widthProperty()).subtract(20));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                event -> tableRecords.get(event.getTablePosition().getRow()).setNameProp(event.getNewValue())
        );
    }

    private void setupTestColumns() {
        testsCol.setText("Tests");
        testsCol.setResizable(false);

        for(int i = 0; i < Student.testSize; i++) {
            createTestColumn(i);
        }
    }

    private void setupProjectColumn() {
        projectCol.setText("Final Project");
        projectCol.setResizable(false);
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
        test.setResizable(false);

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
        testsCol.getColumns().add(test);
    }
}
