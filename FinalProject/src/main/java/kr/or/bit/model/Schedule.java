package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;

public class Schedule {
  private int id;
  private Date start;
  private Date end;
  private String title;
  private String color;
  private int group_id;
  private int course_id;

  private LocalDate startLocal;
  private LocalDate endLocal;

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public LocalDate getStartLocal() {
    return startLocal;
  }

  public void setStartLocal(LocalDate startLocal) {
    this.startLocal = startLocal;
  }

  public LocalDate getEndLocal() {
    return endLocal;
  }

  public void setEndLocal(LocalDate endLocal) {
    this.endLocal = endLocal;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

  public int getCourse_id() {
    return course_id;
  }

  public void setCourse_id(int course_id) {
    this.course_id = course_id;
  }
}
