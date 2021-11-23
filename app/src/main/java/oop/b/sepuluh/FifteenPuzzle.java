/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package oop.b.sepuluh;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FifteenPuzzle extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 640, 480);

        double margin = 16f;
        double unit = (scene.getHeight() - (2 * margin)) * 0.25f;

        Rectangle rectangle = new Rectangle(margin, margin, unit * 4, unit * 4);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setOnMouseClicked(mouseEvent -> System.out.println(mouseEvent.getButton() + " On " + mouseEvent.getX() + ", " + mouseEvent.getY()));
        root.getChildren().add(rectangle);

        int i, j;

        for (i = 0; i < 4; i++){
            for (j = 0; j < 4; j++){
                Rectangle rec = new Rectangle((j * unit) + margin, (i * unit) + margin, unit, unit);
                rec.setFill(Color.WHITE);
                rec.setStroke(Color.BLACK);
                // rec.setOnMouseClicked(mouseEvent -> System.out.println("(" + i + ", " + j + ")"));
                root.getChildren().add(rec);
            }
        }

        Rectangle recMove = new Rectangle((5 * unit) + 32f, 0.5f*unit + 16f, 2 * unit, unit);
        recMove.setFill(Color.GREY);
        recMove.setStroke(Color.BLACK);
        root.getChildren().add(recMove);

        Rectangle recReset = new Rectangle((5 * unit) + 32f, (2.5f * unit) + 16f, 2 * unit, unit);
        recReset.setFill(Color.GREY);
        recReset.setStroke(Color.BLACK);
        root.getChildren().add(recReset);
        
        
        stage.setScene(scene);
        stage.show();
    }

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        launch();
    }

}