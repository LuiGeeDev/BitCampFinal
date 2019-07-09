package kr.or.bit.model;

public class TroubleShooting implements ArticleOption {
  private int id;
  private int issue_closed;
  private int article_id;
  private int group_id;

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
    return article_id;
  }

  public void setArticle_id(int article_id) {
    this.article_id = article_id;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }
}
