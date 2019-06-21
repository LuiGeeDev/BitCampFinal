package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;

public class Project {
  private int id;
  private String project_name;
  private Date start_date;
  private Date end_date;
  private int season;
  private int class_id;
  private LocalDate startLocaldate;
  private LocalDate endLocaldate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getProject_name() {
    return project_name;
  }

  public void setProject_name(String project_name) {
    this.project_name = project_name;
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

  public int getSeason() {
    return season;
  }

  public void setSeason(int season) {
    this.season = season;
  }

  public int getClass_id() {
    return class_id;
  }

  public void setClass_id(int class_id) {
    this.class_id = class_id;
  }

  public LocalDate getStartLocaldate() {
    return startLocaldate;
  }

  public void setStartLocaldate(LocalDate startLocaldate) {
    this.startLocaldate = startLocaldate;
  }

  public LocalDate getEndLocaldate() {
    return endLocaldate;
  }

  public void setEndLocaldate(LocalDate endLocaldate) {
    this.endLocaldate = endLocaldate;
  }

  @Override
  public String toString() {
    return "Project [id=" + id + ", project_name=" + project_name + ", start_date=" + start_date + ", end_date="
        + end_date + ", season=" + season + ", class_id=" + class_id + ", startLocaldate=" + startLocaldate
        + ", endLocaldate=" + endLocaldate + "]";
  }
}
