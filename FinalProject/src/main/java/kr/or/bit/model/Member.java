package kr.or.bit.model;

import java.sql.Date;
import java.util.List;

public class Member {
  private String username;
  private String password;
  private String name;
  private String email;
  private int enabled;
  private int course_id;
  private String profile_photo;
  private int group_id;
  private String role;
  private List<String> scrap;
  private int group_no;
  
  private Date end_date;
  private String course_name;
  
  public String getCourse_name() {
    return course_name;
  }

  public void setCourse_name(String course_name) {
    this.course_name = course_name;
  }

  public Date getEnd_date() {
    return end_date;
  }

  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(int enabled) {
    this.enabled = enabled;
  }

  public int getCourse_id() {
    return course_id;
  }

  public void setCourse_id(int course_id) {
    this.course_id = course_id;
  }

  public String getProfile_photo() {
    return profile_photo;
  }

  public void setProfile_photo(String profile_photo) {
    this.profile_photo = profile_photo;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<String> getScrap() {
    return scrap;
  }

  public void setScrap(List<String> scrap) {
    this.scrap = scrap;
  }

  public int getGroup_no() {
    return group_no;
  }

  public void setGroup_no(int group_no) {
    this.group_no = group_no;
  }
  
  @Override
  public String toString() {
    return "Member [username=" + username + ", password=" + password + ", name=" + name + ", email=" + email
        + ", enabled=" + enabled + ", course_id=" + course_id + ", profile_photo=" + profile_photo + ", group_id="
        + group_id + ", role=" + role + ", scrap=" + scrap + ", group_no=" + group_no + ", end_date=" + end_date
        + ", course_name=" + course_name + "]";
  }
}
