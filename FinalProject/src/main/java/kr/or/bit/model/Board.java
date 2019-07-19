package kr.or.bit.model;

public class Board {
  private int id;
  private String board_name;
  private int boardtype;
  private int course_id;
  private int category;

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  private String typeName;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBoard_name() {
    return board_name;
  }

  public void setBoard_name(String board_name) {
    this.board_name = board_name;
  }

  public int getBoardtype() {
    return boardtype;
  }

  public void setBoardtype(int boardtype) {
    this.boardtype = boardtype;
  }

  public int getCourse_id() {
    return course_id;
  }

  public void setCourse_id(int course_id) {
    this.course_id = course_id;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  @Override
  public String toString() {
    return "Board [id=" + id + ", board_name=" + board_name + ", boardtype=" + boardtype + ", course_id=" + course_id
        + ", category=" + category + ", typeName=" + typeName + "]";
  }
}
