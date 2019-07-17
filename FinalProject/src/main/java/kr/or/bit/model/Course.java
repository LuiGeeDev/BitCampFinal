package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Course {
  private int id;
  private String course_name;
  private Date start_date;
  private Date end_date;
  private int subject;
  private int classroom_id;
  private int people;
  private String classroom_name;
  private String subject_name;
  private LocalDate startDate;
  private LocalDate endDate;
  private List<Board> boards;
  private int count;
  private double divisionResult;
  private String name;
  private String teacher;
  private int students;
  private LocalDate startDateLocal;
  private LocalDate endDateLocal;

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

  public String getTeacher() {
    return teacher;
  }

  public void setTeacher(String teacher) {
    this.teacher = teacher;
  }

  public int getStudents() {
    return students;
  }

  public void setStudents(int students) {
    this.students = students;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getDivisionResult() {
    return divisionResult;
  }

  public void setDivisionResult(double divisionResult) {
    this.divisionResult = divisionResult;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }

  public int getPeople() {
    return people;
  }

  public void setPeople(int people) {
    this.people = people;
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
        + ", subject=" + subject + ", classroom_id=" + classroom_id + ", people=" + people + ", classroom_name="
        + classroom_name + ", subject_name=" + subject_name + ", startDate=" + startDate + ", endDate=" + endDate
        + ", boards=" + boards + ", count=" + count + ", divisionResult=" + divisionResult + ", name=" + name
        + ", teacher=" + teacher + ", students=" + students + ", startDateLocal=" + startDateLocal + ", endDateLocal="
        + endDateLocal + "]";
  }
}
