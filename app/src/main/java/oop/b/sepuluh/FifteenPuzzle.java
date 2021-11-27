/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package oop.b.sepuluh;

import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

public class FifteenPuzzle extends Application {
    private static int puzzleSize = 4;

    private Stage mainStage;
    private Group tileGroup;

    private InnerPuzzle innerPuzzle;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        Group root = new Group();
        Scene scene = new Scene(root, 640, 480);
        mainStage.setScene(scene);
        mainStage.setHeight(scene.getHeight());
        scene.widthProperty().addListener(listener -> drawGrid());
        scene.heightProperty().addListener(listener -> drawGrid());

        double margin = 16f;
        double unit = (scene.getHeight() - (2 * margin)) / puzzleSize;

        tileGroup = new Group();
        root.getChildren().add(tileGroup);
        innerPuzzle = new InnerPuzzle(puzzleSize);
        drawGrid();

        Rectangle recMove = new Rectangle((5 * unit) + 32f, 0.5f*unit + 16f, 2 * unit, unit);
        recMove.setFill(Color.GREY);
        recMove.setStroke(Color.BLACK);
        root.getChildren().add(recMove);

        Rectangle recReset = new Rectangle((5 * unit) + 32f, (2.5f * unit) + 16f, 2 * unit, unit);
        recReset.setFill(Color.GREY);
        recReset.setStroke(Color.BLACK);
        root.getChildren().add(recReset);
        stage.show();
    }

    public void drawGrid() {
        tileGroup.getChildren().clear();

        double margin = 16f;
        double unit = (mainStage.getHeight() - (4 * margin)) / puzzleSize;

        for (int i = 0; i < puzzleSize; i++){
            for (int j = 0; j < puzzleSize; j++){
                if (innerPuzzle.getGrid(i, j) == 0) continue;

                final int row = i, col = j;
                Rectangle rec = new Rectangle(unit - (margin/2), unit - (margin/2));
                rec.setFill(Color.ORANGE);
                rec.setArcHeight(margin);
                rec.setArcWidth(margin);

                Label label = new Label(Integer.toString(innerPuzzle.getGrid(i, j)));
                label.setScaleX(unit / 25);
                label.setScaleY(unit / 25);

                StackPane tile = new StackPane(rec, label);
                tile.setOnMouseClicked(mouseEvent -> onGridClick(col, row));
                tile.setTranslateX((j * unit) + margin);
                tile.setTranslateY((i * unit) + margin);

                tileGroup.getChildren().add(tile);
            }
        }
    }

    private void onGridClick(int col, int row){
        if (innerPuzzle.onClick(col, row)) {
            drawGrid();
            if (innerPuzzle.isSolved()) System.out.println("Solved in " + innerPuzzle.getMoveCounter() + " moves!");
        }
    }

    public static void main(String[] args) {
        if(args.length == 1) 
            puzzleSize = Integer.parseInt(args[0]);

        launch();
    }

}