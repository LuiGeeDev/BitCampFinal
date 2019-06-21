package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;

public class Course {
  private int id;
  private String course_name;
  private Date start_date;
  private Date end_date;
  private int subject;
  private int classroom_id;
  private String classroomName;
  private String subjectName;
  private LocalDate startDate;
  private LocalDate endDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCourse_name() {
    return course_name;
  }

  public void setCourse_name(String course_name) {
    this.course_name = course_name;
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

  public int getSubject() {
    return subject;
  }

  public void setSubject(int subject) {
    this.subject = subject;
  }

  public int getClassroom_id() {
    return classroom_id;
  }

  public void setClassroom_id(int classroom_id) {
    this.classroom_id = classroom_id;
  }

  public String getClassroomName() {
    return classroomName;
  }

  public void setClassroomName(String classroomName) {
    this.classroomName = classroomName;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
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

  @Override
  public String toString() {
    return "Course [id=" + id + ", course_name=" + course_name + ", start_date=" + start_date + ", end_date=" + end_date
        + ", subject=" + subject + ", classroom_id=" + classroom_id + ", classroomName=" + classroomName
        + ", subjectName=" + subjectName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
  }
}
