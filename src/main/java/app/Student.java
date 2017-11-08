package app;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by Parth on 2017-06-21.
 */
public class Student {
    private SimpleStringProperty nameProp = new SimpleStringProperty("");
    private ArrayList<SimpleLongProperty> testScoresProp = new ArrayList<>();
    private SimpleLongProperty projScoreProp = new SimpleLongProperty(-1);
    static int testSize = 0;

    //TODO: make fxml start with 1 test by default and all new students should populate one test score prop
    public Student(String name) {
        this.nameProp.set(name);
    }

    public void setNameProp(String name) {
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

    public void setProjScoreProp(long value) {
        this.projScoreProp.set(value);
    }

    public Long getTestScoresprop(int index) {
        return this.testScoresProp.get(index).get();
    }

    public Long getProjScoreprop() {
        return this.projScoreProp.get();
    }

    public static void addTest() {
        for (Student student : AppCtrl.tableRecords)
            student.testScoresProp.add(new SimpleLongProperty(-1));
        testSize++;
    }
}
