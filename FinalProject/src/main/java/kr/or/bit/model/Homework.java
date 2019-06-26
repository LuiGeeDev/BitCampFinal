package kr.or.bit.model;

import java.sql.Date;

public class Homework implements ArticleOption {
  
  private int id;
  private Date end_date;
  private String file1;
  private String file2;
  private int Article_id;
  
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Date getEnd_date() {
    return end_date;
  }
  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
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
    return "Homework [id=" + id + ", end_date=" + end_date + ", file1=" + file1 + ", file2=" + file2 + ", Article_id="
        + Article_id + "]";
  }
}
