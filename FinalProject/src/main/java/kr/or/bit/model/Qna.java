package kr.or.bit.model;

public class Qna implements ArticleOption {
  private int id;
  private int answerd;
  private int teacher_answered;
  private int article_id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getArticle_id() {
    return article_id;
  }
  public void setArticle_id(int article_id) {
    this.article_id = article_id;
  }
  public int getAnswerd() {
    return answerd;
  }
  public void setAnswerd(int answerd) {
    this.answerd = answerd;
  }
  public int getTeacher_answered() {
    return teacher_answered;
  }
  public void setTeacher_answered(int teacher_answered) {
    this.teacher_answered = teacher_answered;
  }
  @Override
  public String toString() {
    return "Qna [id=" + id + ", answerd=" + answerd + ", teacher_answered=" + teacher_answered + ", article_id="
        + article_id + "]";
  }
}
