package kr.or.bit.model;

import java.sql.Date;
import java.util.List;

public class Homework implements ArticleOption {
  private int id;
  private Date end_date;
  private int file1;
  private int file2;
  private int Article_id;
  private List<Files> files;

  public List<Files> getFiles() {
    return files;
  }

  public void setFiles(List<Files> files) {
    this.files = files;
  }

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

  public int getFile1() {
    return file1;
  }

  public int getFile2() {
    return file2;
  }

  public void setFile1(int file1) {
    this.file1 = file1;
  }

  public void setFile2(int file2) {
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
