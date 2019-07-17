package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Project {
  private int id;
  private String project_name;
  private Date start_date;
  private Date end_date;
  private int season;
  private int course_id;
  private int groups;
  private LocalDate startDateLocal;
  private LocalDate endDateLocal;
  private List<ProjectMember> students;

  public int getGroups() {
    return groups;
  }

  public void setGroups(int groups) {
    this.groups = groups;
  }

  public List<ProjectMember> getStudents() {
    return students;
  }

  public void setStudents(List<ProjectMember> students) {
    this.students = students;
  }

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

  public int getCourse_id() {
    return course_id;
  }

  public void setCourse_id(int course_id) {
    this.course_id = course_id;
  }

  public LocalDate getStartDateLocal() {
    return startDateLocal;
  }

  public void setStartDateLocal(LocalDate startDateLocal) {
    this.startDateLocal = startDateLocal;
  }

  public LocalDate getEndDateLocal() {
    return endDateLocal;
  }

  public void setEndDateLocal(LocalDate endDateLocal) {
    this.endDateLocal = endDateLocal;
  }

  @Override
  public String toString() {
    return "Project [id=" + id + ", project_name=" + project_name + ", start_date=" + start_date + ", end_date="
        + end_date + ", season=" + season + ", course_id=" + course_id + ", groups=" + groups + ", startDateLocal="
        + startDateLocal + ", endDateLocal=" + endDateLocal + ", students=" + students + "]";
  }
}
