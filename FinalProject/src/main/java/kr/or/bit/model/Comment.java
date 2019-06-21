package kr.or.bit.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Comment {
  private int id;
  private String content;
  private Timestamp time;
  private int article_id;
  private String username;
  private LocalDateTime localTime;

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

  public LocalDateTime getLocalTime() {
    return localTime;
  }

  public void setLocalTime(LocalDateTime localTime) {
    this.localTime = localTime;
  }

  @Override
  public String toString() {
    return "Comment [id=" + id + ", content=" + content + ", time=" + time + ", article_id=" + article_id
        + ", username=" + username + ", localTime=" + localTime + "]";
  }
}
