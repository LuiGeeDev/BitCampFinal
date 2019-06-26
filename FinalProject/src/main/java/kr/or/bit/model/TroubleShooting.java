package kr.or.bit.model;

public class TroubleShooting implements ArticleOption {
  
  private int id;
  private int issue_closed;
  private int Article_id;
  private int Group_id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getIssue_closed() {
    return issue_closed;
  }
  public void setIssue_closed(int issue_closed) {
    this.issue_closed = issue_closed;
  }
  public int getArticle_id() {
    return Article_id;
  }
  public void setArticle_id(int article_id) {
    Article_id = article_id;
  }
  public int getGroup_id() {
    return Group_id;
  }
  public void setGroup_id(int group_id) {
    Group_id = group_id;
  }
  @Override
  public String toString() {
    return "TroubleShooting [id=" + id + ", issue_closed=" + issue_closed + ", Article_id=" + Article_id + ", Group_id="
        + Group_id + "]";
  }
}


