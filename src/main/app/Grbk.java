package app;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Parth on 2017-06-18.
 */
public class Grbk extends Application{

    @Override
    public void start(Stage window) {
        window.setTitle("GRBK");

        MongoClient mongo = MongoClients.create();
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
