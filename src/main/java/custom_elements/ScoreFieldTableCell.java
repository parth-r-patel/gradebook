package custom_elements;

import app.Student;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Parth on 2017-06-24.
 */
public class ScoreFieldTableCell extends TableCell<Student, Long> {
    private TextField textField;
    private static Alert inputInvalid;

    public ScoreFieldTableCell() {
        inputInvalid = new Alert(Alert.AlertType.WARNING);
        inputInvalid.setHeaderText(null);
        inputInvalid.setTitle("Invalid Input");
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if(textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        if(!valueOf().equals("-1")) {
            setText(valueOf());
        }
        // TODO: remove this line if canceled value should be default on next edit (change "cache")
        textField.setText(valueOf());
        setGraphic(null);
    }

    @Override
    public void updateItem(Long score, boolean empty) {
        super.updateItem(score, empty);

        if(score == null || score == -1 || empty) {
            getStyleClass().removeAll("score-field-cell");
            getStyleClass().add("score-field-cell-empty");
            setText(null);
            setGraphic(null);
        }
        else{
            getStyleClass().removeAll("score-field-cell-empty");
            getStyleClass().add("score-field-cell");
            if(isEditing()) {
                if(textField != null) {
                    textField.setText(valueOf());
                }
                setText(null);
                setGraphic(textField);
            }
            else {
                setText(valueOf());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(valueOf());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        textField.setOnKeyPressed(
                (KeyEvent t) -> {
                    if (t.getCode() == KeyCode.ENTER) {
                        Long value = getLong(textField.getText());
                        commitEdit(value);
                    }
                    else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
        );
    }

    private Long getLong(String value) {
        try {
            return Long.parseLong(value);
        }
        catch(NumberFormatException e) {
            inputInvalid.setContentText("You must enter a number in this field");
            inputInvalid.show();
            textField.setText(valueOf());
            return getItem();
        }
    }

    private String valueOf() {
        return String.valueOf(getItem());
    }
}
