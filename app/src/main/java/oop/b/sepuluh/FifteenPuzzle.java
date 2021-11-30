package oop.b.sepuluh;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class FifteenPuzzle extends Application {
    private static int puzzleSize = 4;

    private Stage mainStage;
    private Group tileGroup;
    private Group moveCounterGroup;
    private Button resetButton;
   

    private final Color tileColor = Color.valueOf("#4D4D56");
    private final Color backgroundColor = Color.valueOf("#F4C3C2");
    private final Color textColor = backgroundColor;

    private InnerPuzzle innerPuzzle;

    @Override
    public void start(Stage stage) {
        innerPuzzle = new InnerPuzzle(puzzleSize);
        mainStage = stage;

        
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720);
        // Text text = new Text();
        scene.setFill(backgroundColor);

        mainStage.setScene(scene);
        mainStage.setHeight(scene.getHeight());

        scene.widthProperty().addListener(listener -> drawComponents());
        scene.heightProperty().addListener(listener -> drawComponents());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyboardEventHandler);

        tileGroup = new Group();
        moveCounterGroup = new Group();
        resetButton = new Button("Reset");
        

        root.getChildren().addAll(tileGroup, moveCounterGroup, resetButton);
        drawComponents();
        stage.show();
    }

    public void drawComponents() {
        drawGrid();
        drawResetButton();
        drawMoveCounter();
       
    }

    public void drawResetButton() {
        double margin = 16f;
        double unit = (mainStage.getHeight() - (4 * margin)) / puzzleSize;
        double gameplayAreaSize = (puzzleSize * unit) + (2 * margin);
        
        resetButton.setTranslateX((gameplayAreaSize + mainStage.getWidth() - resetButton.getWidth()) / 2);
        resetButton.setTranslateY(2 * unit);
        
        resetButton.setStyle("-fx-background-color: #df8080; -fx-text-fill: #ffffff; -fx-background-radius: 8; -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
        resetButton.setFont(Font.font("Calibri", FontWeight.BOLD, unit / 2));
        
        resetButton.setOnAction((event) -> onResetClick());
    }

    private void onResetClick() {
        innerPuzzle = new InnerPuzzle(puzzleSize);
        drawComponents();
    }

    public void drawMoveCounter() {
        moveCounterGroup.getChildren().clear();

        double margin = 16f;
        double unit = (mainStage.getHeight() - (4 * margin)) / puzzleSize;
        double gameplayAreaSize = (puzzleSize * unit) + (8 * margin);

        Rectangle recMove = new Rectangle(mainStage.getWidth() - gameplayAreaSize - (9 * margin), 1.2f * unit);
        recMove.setFill(Color.valueOf("#DF8080"));
        recMove.setArcWidth(50.0);
        recMove.setArcHeight(50.0);

        Label moveCounter = new Label(Integer.toString(innerPuzzle.getMoveCounter()));
        moveCounter.setFont(Font.font("Calibri", FontWeight.BOLD, unit / 2));
        moveCounter.setTextFill(Color.WHITE);

        StackPane movePane = new StackPane(recMove, moveCounter);
        movePane.setTranslateX(gameplayAreaSize + margin);
        movePane.setTranslateY(7 * margin);

        
        moveCounterGroup.getChildren().add(movePane);
    }

    public void drawGrid() {
        tileGroup.getChildren().clear();

        double margin = 16f;
        double unit = (mainStage.getHeight() - (4 * margin)) / puzzleSize;

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                if (innerPuzzle.getGrid(i, j) == 0)
                    continue;

                final int row = i, col = j;
                Rectangle rec = new Rectangle(unit - (margin / 2), unit - (margin / 2));
                rec.setFill(tileColor);
                rec.setArcHeight(margin);
                rec.setArcWidth(margin);

                Label label = new Label(Integer.toString(innerPuzzle.getGrid(i, j)));
                label.setFont(Font.font("Calibri", FontWeight.BLACK, unit / 2));
                label.setTextFill(textColor);

                StackPane tile = new StackPane(rec, label);
                tile.setOnMouseClicked(mouseEvent -> onGridClick(col, row));
                tile.setTranslateX((j * unit) + margin);
                tile.setTranslateY((i * unit) + margin);

                tileGroup.getChildren().add(tile);
            }
        }
    }

    private void onGridClick(int col, int row) {
        if (innerPuzzle.onClick(col, row)) {
            drawComponents();
        }
    }

    EventHandler<KeyEvent> keyboardEventHandler = new EventHandler<KeyEvent>() { 
        @Override 
        public void handle(KeyEvent e) {
            if (e.getCode().ordinal() >= KeyCode.LEFT.ordinal() 
                && e.getCode().ordinal() <= KeyCode.DOWN.ordinal() 
                && innerPuzzle.onKeyPressed(e.getCode())
            ) drawComponents();
        } 
     };  

    public static void main(String[] args) {
        if (args.length == 1)
            puzzleSize = Integer.parseInt(args[0]);

        launch();
    }
}