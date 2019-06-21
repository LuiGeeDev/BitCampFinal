package kr.or.bit.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Comment {
  private int id;
  private String content;
  private Timestamp time;
  private Timestamp updated_time;
  private int article_id;
  private String username;
  
  private LocalDateTime timeLocal;
  private LocalDateTime updatedTimeLocal;

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

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Timestamp getUpdated_time() {
    return updated_time;
  }

  public void setUpdated_time(Timestamp updated_time) {
    this.updated_time = updated_time;
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

  public LocalDateTime getTimeLocal() {
    return timeLocal;
  }

  public void setTimeLocal(LocalDateTime timeLocal) {
    this.timeLocal = timeLocal;
  }

  public LocalDateTime getUpdatedTimeLocal() {
    return updatedTimeLocal;
  }

  public void setUpdatedTimeLocal(LocalDateTime updatedTimeLocal) {
    this.updatedTimeLocal = updatedTimeLocal;
  }
}
