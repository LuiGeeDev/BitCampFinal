package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;

public class Schedule {
  private int id;
  private Date start_date;
  private Date end_date;
  private LocalDate startDate;
  private LocalDate endDate;
  private String content;
  private String color;
  private int group_id;
  private int course_id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getStart_date() {
    return start_date;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  public Date getEnd_date() {
    return end_date;
  }

  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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

  @Override
  public String toString() {
    return "Schedule [id=" + id + ", start_date=" + start_date + ", end_date=" + end_date + ", startDate=" + startDate
        + ", endDate=" + endDate + ", content=" + content + ", color=" + color + ", group_id=" + group_id
        + ", course_id=" + course_id + "]";
  }
}
