package kr.or.bit.model;

public class Scrap {
  private int article_id;
  private String username;
  
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
  
  @Override
  public String toString() {
    return "Scrap [article_id=" + article_id + ", username=" + username + "]";
  }
  
}
