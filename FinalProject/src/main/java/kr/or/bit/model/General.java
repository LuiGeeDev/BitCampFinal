package kr.or.bit.model;

public class General implements ArticleOption {
  
  private int id;
  private String file1;
  private String file2;
  private int Article_id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getFile1() {
    return file1;
  }
  public void setFile1(String file1) {
    this.file1 = file1;
  }
  public String getFile2() {
    return file2;
  }
  public void setFile2(String file2) {
    this.file2 = file2;
  }
  public int getArticle_id() {
    return Article_id;
  }
  public void setArticle_id(int article_id) {
    Article_id = article_id;
  }
  @Override
  public String toString() {
    return "General [id=" + id + ", file1=" + file1 + ", file2=" + file2 + ", Article_id=" + Article_id + "]";
  }
  
  
}
