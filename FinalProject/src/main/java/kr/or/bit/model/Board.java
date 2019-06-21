package kr.or.bit.model;

public class Board {
  private int id;
  private String board_name;
  private int boardtype;
  private int course_id;
  private String typename;
  
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
  public String getTypename() {
    return typename;
  }
  public void setTypename(String typename) {
    this.typename = typename;
  }
}
