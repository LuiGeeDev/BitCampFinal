package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;

public class Comment {
  private int id;
  private String content;
  private int article_id;
  private String username;
  private Date time;
  private Date updatedTime;
  private LocalDate timeLocal;
  private LocalDate updatedTimeLocal;

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

  public int getArticle_id() {
    return article_id;
  }

  public void setArticle_id(int article_id) {
    this.article_id = article_id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Date getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }

  public LocalDate getTimeLocal() {
    return timeLocal;
  }

  public void setTimeLocal(LocalDate timeLocal) {
    this.timeLocal = timeLocal;
  }

  public LocalDate getUpdatedTimeLocal() {
    return updatedTimeLocal;
  }

  public void setUpdatedTimeLocal(LocalDate updatedTimeLocal) {
    this.updatedTimeLocal = updatedTimeLocal;
  }
}
