package kr.or.bit.model;

import java.time.LocalDateTime;

import com.google.protobuf.Timestamp;

public class Checklist {
  private int id;
  private String content;
  private int checked;
  private Timestamp time;
  private String writer_username;
  private String checker_username;
  private int group_id;
  
  private LocalDateTime timeLocal;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getChecked() {
    return checked;
  }

  public void setChecked(int checked) {
    this.checked = checked;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getWriter_username() {
    return writer_username;
  }

  public void setWriter_username(String writer_username) {
    this.writer_username = writer_username;
  }

  public String getChecker_username() {
    return checker_username;
  }

  public void setChecker_username(String checker_username) {
    this.checker_username = checker_username;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

  public LocalDateTime getTimeLocal() {
    return timeLocal;
  }

  public void setTimeLocal(LocalDateTime timeLocal) {
    this.timeLocal = timeLocal;
  }

  @Override
  public String toString() {
    return "Checklist [id=" + id + ", content=" + content + ", checked=" + checked + ", time=" + time
        + ", writer_username=" + writer_username + ", checker_username=" + checker_username + ", group_id=" + group_id
        + ", timeLocal=" + timeLocal + "]";
  }
}
