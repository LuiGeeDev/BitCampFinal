package kr.or.bit.model;

import java.util.List;

public class BoardAddRemove {
  private List<String> boardToAdd;
  private List<String> boardToRemove;

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