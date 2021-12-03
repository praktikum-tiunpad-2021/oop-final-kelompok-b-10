package oop.b.sepuluh;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class FifteenPuzzle extends Application {
    private static int puzzleSize = 4;

    private Stage mainStage;
    private GridPane tileGroup;
    private Group moveCounterGroup;
    private Button resetButton;

    private Font font;

    private final double margin = 16f;
    private double unit;
    private double gameplayAreaSize;

    private InnerPuzzle innerPuzzle;

    EventHandler<KeyEvent> keyboardEventHandler = new EventHandler<KeyEvent>() { 
        @Override 
        public void handle(KeyEvent e) {
            if (e.getCode().ordinal() >= KeyCode.LEFT.ordinal() 
                && e.getCode().ordinal() <= KeyCode.DOWN.ordinal() 
                && innerPuzzle.onKeyPressed(e.getCode())
            ) drawComponents();
        }
    };

    @Override
    public void start(Stage stage) {
        innerPuzzle = new InnerPuzzle(puzzleSize);
        mainStage = stage;

        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add("oop/b/sepuluh/style.css");

        mainStage.setScene(scene);
        mainStage.setHeight(scene.getHeight());

        mainStage.widthProperty().addListener(listener -> drawComponents());
        mainStage.heightProperty().addListener(listener -> drawComponents());
        mainStage.addEventFilter(KeyEvent.KEY_PRESSED, keyboardEventHandler);

        initGrid();
        moveCounterGroup = new Group();
        resetButton = new Button("Reset");

        root.getChildren().addAll(tileGroup, moveCounterGroup, resetButton);
        drawComponents();
        stage.show();
    }

    public void drawComponents() {
        unit = (mainStage.getHeight() - (4 * margin)) / puzzleSize;
        gameplayAreaSize = (puzzleSize * unit) + (8 * margin);
        font = Font.font("Calibri", FontWeight.BOLD, unit / 2);

        drawGrid();
        drawResetButton();
        drawMoveCounter();
    }

    public void drawResetButton() {
        resetButton.setTranslateX((gameplayAreaSize + mainStage.getWidth() - resetButton.getWidth()) / 2);
        resetButton.setTranslateY(2.5 * unit);

        resetButton.setFont(font);
        resetButton.setOnAction((event) -> onResetClick());
    }

    private void onResetClick() {
        innerPuzzle = new InnerPuzzle(puzzleSize);
        drawComponents();
    }

    public void drawMoveCounter() {
        moveCounterGroup.getChildren().clear();

        Rectangle recMove = new Rectangle(mainStage.getWidth() - gameplayAreaSize - (9 * margin), 1.1f * unit);
        recMove.getStyleClass().add("move-counter");

        Label moveCounter = new Label(Integer.toString(innerPuzzle.getMoveCounter()));
        moveCounter.setFont(font);

        StackPane movePane = new StackPane(recMove, moveCounter);
        movePane.setTranslateX(gameplayAreaSize + margin);
        movePane.setTranslateY(9 * margin);

        Rectangle recText = new Rectangle(mainStage.getWidth() - gameplayAreaSize - (9 * margin), 1f * unit);
        recText.setFill(Color.TRANSPARENT);

        Label textMove = new Label("Move");
        textMove.setFont(font);
        textMove.getStyleClass().addAll("move-label");

        StackPane textPane = new StackPane(recText, textMove);
        textPane.setTranslateX(gameplayAreaSize + margin);
        textPane.setTranslateY(1 * margin);

        moveCounterGroup.getChildren().add(movePane);
        moveCounterGroup.getChildren().add(textPane);  
    }

    public void initGrid() {
        tileGroup = new GridPane();

        tileGroup.setPadding(new Insets(margin, margin, margin, margin));
        tileGroup.setHgap(margin / 2);
        tileGroup.setVgap(margin / 2);
    }

    public void drawGrid() {
        tileGroup.getChildren().clear();

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                if (innerPuzzle.getGrid(i, j) == 0)
                    continue;

                final int row = i, col = j;
                Rectangle rec = new Rectangle(unit - (margin / 2), unit - (margin / 2));
                rec.getStyleClass().add("tile");

                Label label = new Label(Integer.toString(innerPuzzle.getGrid(i, j)));
                label.setFont(font);

                StackPane tile = new StackPane(rec, label);
                tile.setOnMouseClicked(mouseEvent -> onGridClick(col, row));

                tileGroup.add(tile, col, row);
            }
        }
    }

    private void onGridClick(int col, int row) {
        if (innerPuzzle.onClick(col, row)) {
            drawComponents();
        }
    }

    public static void main(String[] args) {
        if (args.length == 1)
            puzzleSize = Integer.parseInt(args[0]);

        launch();
    }
}