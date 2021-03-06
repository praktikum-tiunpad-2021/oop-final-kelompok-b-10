package oop.b.sepuluh;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FifteenPuzzle extends Application {
    private static int puzzleSize = 4;

    private Stage mainStage;
    private GridPane tileGroup;

    private VBox statsPane;
    private VBox moveCounterGroup;
    private Button resetButton;

    private final double margin = 16f;
    private double unit;
    private Font font;

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

        HBox root = new HBox();
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add("oop/b/sepuluh/style.css");
        
        mainStage.setTitle("Fifteen Puzzle"); 
        mainStage.getIcons().add(new Image("oop/b/sepuluh/icon.png"));
        mainStage.setMinHeight(480);
        mainStage.setMinWidth(480 * 16 / 9);

        mainStage.setScene(scene);
        mainStage.setHeight(scene.getHeight());

        mainStage.widthProperty().addListener(listener -> drawComponents());
        mainStage.heightProperty().addListener(listener -> drawComponents());
        mainStage.addEventFilter(KeyEvent.KEY_PRESSED, keyboardEventHandler);

        initGrid();
        initStatsPane();

        root.getChildren().addAll(tileGroup, statsPane);
        HBox.setHgrow(statsPane, Priority.ALWAYS);

        drawComponents();
        stage.show();
    }

    public void initStatsPane() {
        moveCounterGroup = new VBox(margin);
        moveCounterGroup.setAlignment(Pos.BASELINE_CENTER);

        resetButton = new Button("Reset");
        resetButton.setOnAction((event) -> onResetClick());

        statsPane = new VBox(moveCounterGroup, resetButton);
        statsPane.setAlignment(Pos.TOP_CENTER);
    }

    public void drawComponents() {
        unit = mainStage.getHeight() / 4;
        font = Font.font("Calibri", FontWeight.BOLD, unit / 2);

        statsPane.setSpacing(unit * 0.75f);

        drawGrid();
        drawResetButton();
        drawMoveCounter();
    }

    public void drawResetButton() {
        resetButton.setFont(font);
    }

    private void onResetClick() {
        innerPuzzle = new InnerPuzzle(puzzleSize);
        drawComponents();
    }

    public void drawMoveCounter() {
        moveCounterGroup.getChildren().clear();

        Rectangle recMove = new Rectangle((mainStage.getWidth() - mainStage.getHeight()) * 0.75f, unit);
        recMove.getStyleClass().add("move-counter");

        Label moveCounter = new Label(Integer.toString(innerPuzzle.getMoveCounter()));
        moveCounter.setFont(font);

        StackPane movePane = new StackPane(recMove, moveCounter);

        Label textMove = new Label("Move");
        textMove.setFont(font);
        textMove.getStyleClass().addAll("move-label");

        moveCounterGroup.getChildren().addAll(textMove, movePane);
    }

    public void initGrid() {
        tileGroup = new GridPane();

        tileGroup.setPadding(new Insets(margin, margin, margin, margin));
        tileGroup.setHgap(margin / 2);
        tileGroup.setVgap(margin / 2);
    }

    public void drawGrid() {
        tileGroup.getChildren().clear();
        double tileUnit = (mainStage.getHeight() - (4 * margin)) / puzzleSize;

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                if (innerPuzzle.getGrid(i, j) == 0)
                    continue;

                final int row = i, col = j;
                Rectangle rec = new Rectangle(tileUnit - (margin / 2), tileUnit - (margin / 2));
                rec.getStyleClass().add("tile");

                Label label = new Label(Integer.toString(innerPuzzle.getGrid(i, j)));
                label.setFont(Font.font("Calibri", FontWeight.BOLD, tileUnit / 2));

                StackPane tile = new StackPane(rec, label);
                tile.getStyleClass().add("tile");
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
        if (args.length == 1){
            int temp = Integer.parseInt(args[0]);
            if (temp >= 2 && temp <= 8) puzzleSize = temp; 
        }

        launch();
    }
}