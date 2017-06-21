package app;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.Optional;

/**
 * Created by Parth on 2017-06-18.
 */
public class AppCtrl {
    @FXML private TableView<Record> recordsTable;
    @FXML private TableColumn<Record, String> nameCol;
    @FXML private TableColumn<Record, String> test1Col;

    private Alert inputInvalid;

    public AppCtrl() {
        inputInvalid = new Alert(Alert.AlertType.WARNING);
        inputInvalid.setHeaderText(null);
        inputInvalid.setTitle("Invalid Input");
    }

    @FXML
    public void initialize() {
        Record r1 = new Record();
        r1.setName("letap Parth");
        r1.getAllTestScores().add(new SimpleLongProperty(9));
        Record r2 = new Record();
        r2.setName("letap");
        ObservableList<Record> tableRecords = FXCollections.observableArrayList();
        tableRecords.add(r1);
        tableRecords.add(r2);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameProp"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                event -> tableRecords.get(event.getTablePosition().getRow()).setName(event.getNewValue())
        );

        // TODO: update the hardcoded indices to be dynamic column indices
        test1Col.setCellValueFactory(
                param -> {
                    int size = param.getValue().getAllTestScores().size();
                    return size == 0 ? null : new ReadOnlyStringWrapper(String.valueOf(param.getValue().getTestScoresprop(0)));
                }
        );
        test1Col.setCellFactory(TextFieldTableCell.forTableColumn());
        test1Col.setOnEditCommit(
                event -> {
                    try {
                        long newV = Long.parseLong(event.getNewValue());
                        tableRecords.get(event.getTablePosition().getRow()).setTestScoresProp(0, newV);
                    }
                    catch (NumberFormatException e) {
                        inputInvalid.setContentText("You must enter a number in this field");
                        inputInvalid.showAndWait();
                        event.getTableView().refresh();
                    }
                }
        );

        recordsTable.setItems(tableRecords);
    }
}
