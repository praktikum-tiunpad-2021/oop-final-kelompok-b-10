package oop.b.sepuluh;

import java.util.ArrayList;
import java.util.Collections;

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

  public void onClick(int boxX, int boxY){
    if(boxX == zeroLocationX){
      if (boxY == zeroLocationY + 1) moveZeroDown();
      else if (boxY == zeroLocationY - 1) moveZeroUp();
    }
    else if (boxY == zeroLocationY){
      if (boxX == zeroLocationX + 1) moveZeroRight();
      else if (boxX == zeroLocationX - 1) moveZeroLeft();
    }
  }

  public int getMoveCounter(){
    return moveCounter;
  }

  private void moveZeroUp(){
    if (zeroLocationY == 0) return;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY - 1).get(zeroLocationX));
    grid.get(zeroLocationY - 1).set(zeroLocationX, 0);
    zeroLocationY--;
    moveCounter++;
  }

  private void moveZeroDown(){
    if (zeroLocationY == puzzleSize - 1) return;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY + 1).get(zeroLocationX));
    grid.get(zeroLocationY + 1).set(zeroLocationX, 0);
    zeroLocationY++;
    moveCounter++;
  }

  private void moveZeroLeft(){
    if (zeroLocationX == 0) return;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY).get(zeroLocationX - 1));
    grid.get(zeroLocationY).set(zeroLocationX - 1, 0);
    zeroLocationX--;
    moveCounter++;
  }

  private void moveZeroRight(){
    if (zeroLocationX == puzzleSize - 1) return;

    grid.get(zeroLocationY).set(zeroLocationX, grid.get(zeroLocationY).get(zeroLocationX + 1));
    grid.get(zeroLocationY).set(zeroLocationX + 1, 0);
    zeroLocationX++;
    moveCounter++;
  }

  public Integer getGrid(int row, int col){
    return grid.get(row).get(col);
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
    int zeroLocation = 0;
    while (grid1D.get(zeroLocation) != 0) zeroLocation++;

    int invCount = 0;
    for (int i = 0; i < puzzleSize - 1; i++){
      for (int j = i + 1; j < puzzleSize; j++){
        if (grid1D.get(j) != 0 && grid1D.get(i) != 0 && grid1D.get(i) > grid1D.get(j))
          invCount++;
      }
    }

    zeroLocation /= puzzleSize;
    if (puzzleSize % 2 == 1){
      if (invCount % 2 == 0) return true;
    }
    else {
      if (zeroLocation % 2 == 0 && invCount % 2 == 1) return true;
      if (zeroLocation % 2 == 1 && invCount % 2 == 0) return true;
    }
    return false;
  }
}
