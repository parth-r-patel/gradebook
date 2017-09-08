package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Parth on 2017-06-18.
 */
public class Grbk extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage window) throws Exception {
        primaryStage = window;
        primaryStage.setTitle("GRBK");

        Parent root = FXMLLoader.load(getClass().getResource("/app.fxml"));

        Scene s_main = new Scene(root, 800, 800);
        primaryStage.setScene(s_main);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
