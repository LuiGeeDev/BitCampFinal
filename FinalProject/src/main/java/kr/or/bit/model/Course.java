package kr.or.bit.model;

import java.sql.Date;

public class Course {
  private int id;
  private String course_name;
  private Date start_date;
  private Date end_date;
  private int subject;
  private int classroom_id;
  private String classroom_name;
  private String subject_name;

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

  public String getClassroom_name() {
    return classroom_name;
  }

  public void setClassroom_name(String classroom_name) {
    this.classroom_name = classroom_name;
  }

  public String getSubject_name() {
    return subject_name;
  }

  public void setSubject_name(String subject_name) {
    this.subject_name = subject_name;
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", course_name=" + course_name + ", start_date=" + start_date + ", end_date=" + end_date
        + ", subject=" + subject + ", classroom_id=" + classroom_id + ", classroom_name=" + classroom_name
        + ", subject_name=" + subject_name + "]";
  }
}
