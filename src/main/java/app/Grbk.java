package app;

import custom_elements.FrostedRegion;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Parth on 2017-06-18.
 */
public class Grbk extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage window) throws Exception {
//        Platform.setImplicitExit(false);

        primaryStage = window;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("GRBK");

//        Storage.connectMongoClient();
//        Storage.getStudents();

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: transparent");

        Pane frostContainer = new Pane();
        frostContainer.setStyle("-fx-background-color: #5dbbac");
        frostContainer.setOpacity(0.97);
        root.getChildren().add(frostContainer);

        Scene s_main = new Scene(root, 800, 800, Color.TRANSPARENT);
        s_main.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());

        Pane appFxml = FXMLLoader.load(getClass().getResource("/app.fxml"));
        FrostedRegion frost = new FrostedRegion(primaryStage, appFxml, frostContainer);
        frost.setMinSize(800, 800);
        root.getChildren().addAll(frost, appFxml);

        primaryStage.setScene(s_main);
        primaryStage.show();

        frost.makeDragabble();
    }

    @Override
    public void stop() {
//        Storage.cleanup();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
