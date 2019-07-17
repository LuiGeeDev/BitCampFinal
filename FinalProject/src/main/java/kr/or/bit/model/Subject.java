package kr.or.bit.model;

public class Subject {
  private int id;
  private String subject;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  @Override
  public String toString() {
    return "Subject [id=" + id + ", subject=" + subject + "]";
  }
}
