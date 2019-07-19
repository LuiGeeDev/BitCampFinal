package kr.or.bit.model;

import java.util.List;

public class BoardAddRemove {
  private List<String> boardToAdd;
  private List<String> boardToRemove;
  private List<String> boardCategoryToAdd;

  @Override
  public String toString() {
    return "BoardAddRemove [boardToAdd=" + boardToAdd + ", boardToRemove=" + boardToRemove + ", boardCategoryToAdd="
        + boardCategoryToAdd + "]";
  }

  public List<String> getBoardCategoryToAdd() {
    return boardCategoryToAdd;
  }

  public void setBoardCategoryToAdd(List<String> boardCategoryToAdd) {
    this.boardCategoryToAdd = boardCategoryToAdd;
  }

  public List<String> getBoardToAdd() {
    return boardToAdd;
  }

  public List<String> getBoardToRemove() {
    return boardToRemove;
  }

  public void setBoardToAdd(List<String> boardToAdd) {
    this.boardToAdd = boardToAdd;
  }

  public void setBoardToRemove(List<String> boardToRemove) {
    this.boardToRemove = boardToRemove;
  }
}