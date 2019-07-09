package kr.or.bit.model;

import java.sql.Date;

public class ViewCount {
  private String username;
  private int article_id;
  private Date date;
  
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public int getArticle_id() {
    return article_id;
  }
  public void setArticle_id(int article_id) {
    this.article_id = article_id;
  }
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }

  
  
}
