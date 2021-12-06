package oop.b.sepuluh;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.input.KeyCode;

public class InnerPuzzle {
  private ArrayList<ArrayList<Integer>> grid;
  private int moveCounter;
  private int zeroLocationX;
  private int zeroLocationY;
  private int puzzleSize;

  public InnerPuzzle(int puzzleSize){
    this.puzzleSize = puzzleSize;
    initGrid();
    moveCounter = 0;
  }

  /**
   * true jika merupakan move legal (persis bersebelahan, tidak out of bounds), selain itu false
   */
  public boolean onClick(int boxX, int boxY){
    if (isSolved()) return false;
    if(boxX == zeroLocationX){
      if (boxY == zeroLocationY + 1) return moveZeroDown();
      if (boxY == zeroLocationY - 1) return moveZeroUp();
    }
    if (boxY == zeroLocationY){
      if (boxX == zeroLocationX + 1) return moveZeroRight();
      if (boxX == zeroLocationX - 1) return moveZeroLeft();
    }
    return false;
  }

  public boolean onKeyPressed(KeyCode code){
    if (isSolved()) return false;
    if (code == KeyCode.UP) return moveZeroUp();
    if (code == KeyCode.DOWN) return moveZeroDown();
    if (code == KeyCode.RIGHT) return moveZeroRight();
    if (code == KeyCode.LEFT) return moveZeroLeft();
    return false;
  }

  public int getMoveCounter(){
    return moveCounter;
  }

  private boolean moveZeroUp(){
    if (zeroLocationY == 0) return false;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY - 1).get(zeroLocationX));
    grid.get(zeroLocationY - 1).set(zeroLocationX, 0);
    zeroLocationY--;
    moveCounter++;
    return true;
  }

  private boolean moveZeroDown(){
    if (zeroLocationY == puzzleSize - 1) return false;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY + 1).get(zeroLocationX));
    grid.get(zeroLocationY + 1).set(zeroLocationX, 0);
    zeroLocationY++;
    moveCounter++;
    return true;
  }

  private boolean moveZeroLeft(){
    if (zeroLocationX == 0) return false;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY).get(zeroLocationX - 1));
    grid.get(zeroLocationY).set(zeroLocationX - 1, 0);
    zeroLocationX--;
    moveCounter++;
    return true;
  }

  private boolean moveZeroRight(){
    if (zeroLocationX == puzzleSize - 1) return false;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY).get(zeroLocationX + 1));
    grid.get(zeroLocationY).set(zeroLocationX + 1, 0);
    zeroLocationX++;
    moveCounter++;
    return true;
  }

  public Integer getGrid(int x, int y){
    return grid.get(x).get(y);
  }

  private void initGrid(){
    grid = new ArrayList<ArrayList<Integer>>(4);

    ArrayList<Integer> grid1D = new ArrayList<Integer>(puzzleSize * puzzleSize);
    for (int i = 0; i < puzzleSize * puzzleSize; i++) grid1D.add(i);

    Collections.shuffle(grid1D);
    while (!isSolvable(grid1D)) Collections.shuffle(grid1D);

    int initialGridCounter = 0;
    for (int i = 0; i < puzzleSize; i++){
      ArrayList<Integer> row = new ArrayList<Integer>(puzzleSize);
      for (int j = 0; j < puzzleSize; j++){
        row.add(grid1D.get(initialGridCounter));
        initialGridCounter++;
      }
      grid.add(row);
    }

    for (int i = 0; i < puzzleSize; i++){
      for (int j = 0; j < puzzleSize; j++){
        if (grid.get(i).get(j) == 0){
          zeroLocationX = j;
          zeroLocationY = i;
        }
      }
    }
  }

  private boolean isSolvable(ArrayList<Integer> grid1D){
    int invCount = 0;
    for (int i = 0; i < (puzzleSize * puzzleSize) - 1; i++){
      if (grid1D.get(i) == 0) continue;

      for (int j = i + 1; j < puzzleSize * puzzleSize; j++){
        if (grid1D.get(j) == 0) continue;
        if (grid1D.get(i) > grid1D.get(j)) invCount++;
      }
    }

    if (puzzleSize % 2 == 1){
      if (invCount % 2 == 0) return true;
    }
    else {
      int zeroLocation = 0;
      while (grid1D.get(zeroLocation) != 0) zeroLocation++;
      zeroLocation /= puzzleSize;

      if (zeroLocation % 2 == 0 && invCount % 2 == 1) return true;
      if (zeroLocation % 2 == 1 && invCount % 2 == 0) return true;
    }
    return false;
  }

  public boolean isSolved(){
    int counter = 1;
    for (int i = 0; i < puzzleSize; i++){
      for (int j = 0; j < puzzleSize; j++){
        if (counter != puzzleSize * puzzleSize && getGrid(i, j) != counter) return false;
        counter++;
      }
    }
    return true;
  }
}