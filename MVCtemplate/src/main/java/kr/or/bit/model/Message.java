package kr.or.bit.model;

public class Message {
  private String writer;
  private String content;
  private String boardWriter;

  public String getBoardWriter() {
    return boardWriter;
  }

  public void setBoardWriter(String boardWriter) {
    this.boardWriter = boardWriter;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
