/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package oop.b.sepuluh;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FifteenPuzzle extends Application {

    @Override
    public void start(Stage stage) {
        Label l = new Label(new FifteenPuzzle().getGreeting());
        Scene scene = new Scene(new StackPane(l), 640, 480);
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