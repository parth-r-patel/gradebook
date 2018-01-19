package custom_elements;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Parth on 2017-11-06.
 */
public class FrostedRegion extends Region {

    private final Stage stage;
    private final Pane appContainer;
    private final Pane container;
    private final BoxBlur blurEffect;
    private final double blurAmount = 15;
    private final int blurIterations = 1;
    private WritableImage image;
    private static Robot robot;
    private static final Rectangle2D screen = Screen.getPrimary().getVisualBounds();

    public FrostedRegion(Stage stage, Pane app, Pane container) {
        this.stage = stage;
        this.appContainer = app;
        this.container = container;
        blurEffect = new BoxBlur(blurAmount, blurAmount, blurIterations);
        this.setEffect(blurEffect);
        blurEffect.setInput(new ColorAdjust(0.0, 0.0, 0.4, 2));
        updateBackground();
//        this.getChildren().add(new Button("test"));
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long time) {
//                updateBackground();
//            }
//        };
//        timer.start();
    }

    public void updateBackground() {
        if (this.getWidth() < 1 || this.getHeight() < 1 || this.getOpacity() == 0) {
            return;
        }

        // create the snapshot parameters (defines viewport)
        SnapshotParameters sp = new SnapshotParameters();
        Rectangle2D rect = new Rectangle2D(0, 0, getWidth(), getHeight());
        sp.setViewport(rect);

        this.image = new WritableImage((int) this.getWidth(), (int) this.getHeight());
        // create the snapshot
        this.container.snapshot(sp, image);
        this.setOpacity(0.9);


        // create the backgrouhnd image
        BackgroundImage backgroundImg = new BackgroundImage(this.image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        this.setBackground(new Background(backgroundImg));
    }

    public void makeDragabble() {
        final Delta dragDelta = new Delta();

        Node tp = this.appContainer.getChildren().get(0);

        tp.setOnMousePressed(mouseEvent -> {
            dragDelta.x = this.stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = this.stage.getY() - mouseEvent.getScreenY();
            this.appContainer.setCursor(Cursor.MOVE);
        });

        tp.setOnMouseReleased(mouseEvent -> {
            this.appContainer.setCursor(Cursor.DEFAULT);
        });

        tp.setOnMouseDragged(mouseEvent -> {
            this.stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            this.stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }

    private class Delta {
        double x, y;
    }
}
