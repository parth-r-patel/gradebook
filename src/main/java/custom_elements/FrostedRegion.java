package custom_elements;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
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

    private final Stage container;
    private final Node appContainer;
    private final BoxBlur blurEffect;
    private int x, y, width, height;
    private final double blurAmount = 15;
    private final int blurIterations = 100;
    private Image image;
    private ImageView bgImage;

    public FrostedRegion(Stage stage, Node app) {
        this.container = stage;
        this.appContainer = app;
        blurEffect = new BoxBlur(blurAmount, blurAmount, blurIterations);
        this.setEffect(blurEffect);
        blurEffect.setInput(new ColorAdjust(0.0, 0.0, 0.5, 0.0));

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        File file = new File("chart.png");
        try {
            Robot robot = new java.awt.Robot();
            BufferedImage image = robot.createScreenCapture(new java.awt.Rectangle((int)screen.getMinX(), (int)screen.getMinY(), (int)screen.getWidth(), (int)screen.getHeight()));

            this.image = SwingFXUtils.toFXImage(image, null);
            ImageIO.write(SwingFXUtils.fromFXImage(this.image, null), "png", file);
            this.bgImage = new ImageView(this.image);
            Rectangle2D vp = new Rectangle2D(this.container.getX(), this.container.getY(), this.container.getWidth(), this.container.getHeight());
            System.out.println(vp);
            this.bgImage.setViewport(vp);
            ((StackPane) app).getChildren().add(bgImage);
        } catch (Exception e) {
            System.out.println("The robot of doom strikes!");
            e.printStackTrace();
        }

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long time) {
//                updateBackground();
            }
        };

//        timer.start();
    }

    public void updateBackground() {
        if (this.getWidth() < 1 || this.getHeight() < 1 || this.getOpacity() == 0 || this.x == this.container.getX() || this.y == this.container.getY()) {
            return;
        }

        this.x = (int) this.container.getX();
        this.y = (int) this.container.getY();
        this.width = (int) this.container.getWidth();
        this.height = (int) this.container.getHeight();

        //TODO: change to image view and move viewport as dragged
//        ImageView bgImage = new ImageView(image);
//        bgImage.setViewport();
    }

    public void makeDragabble() {
        final BooleanProperty inDrag = new SimpleBooleanProperty(false);
        final Delta dragDelta = new Delta();

        this.appContainer.setOnMousePressed(mouseEvent -> {
            dragDelta.x = this.container.getX() - mouseEvent.getScreenX();
            dragDelta.y = this.container.getY() - mouseEvent.getScreenY();
            this.appContainer.setCursor(Cursor.MOVE);
        });

        this.appContainer.setOnMouseReleased(mouseEvent -> {
            this.appContainer.setCursor(Cursor.DEFAULT);

            if (inDrag.get()) {
//                this.container.hide();
            }

            inDrag.set(false);
//            this.container.show();
        });

        this.appContainer.setOnMouseDragged(mouseEvent -> {
            this.container.setX(mouseEvent.getScreenX() + dragDelta.x);
            this.container.setY(mouseEvent.getScreenY() + dragDelta.y);
            this.bgImage.setViewport(new Rectangle2D(mouseEvent.getScreenX() + dragDelta.x, mouseEvent.getScreenY() + dragDelta.y, this.container.getWidth(), this.container.getHeight()));
            inDrag.set(true);
//            this.container.hide();
        });
    }

    private class Delta {
        double x, y;
    }
}
