package app;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by Parth on 2017-06-21.
 */
public class Record {
    private SimpleStringProperty nameProp = new SimpleStringProperty("");
    private ArrayList<SimpleLongProperty> testScoresProp = new ArrayList<>();

    public void setName(String name) {
        this.nameProp.set(name);
    }

    public String getNameProp() {
        return this.nameProp.get();
    }

    public ArrayList<SimpleLongProperty> getAllTestScores() {
        return testScoresProp;
    }

    public void setTestScoresProp(int index, long value) {
        this.testScoresProp.get(index).set(value);
    }

    public Long getTestScoresprop(int index) {
        return this.testScoresProp.get(index).get();
    }
}
