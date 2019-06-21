package kr.or.bit.model;

import java.sql.Date;
import java.time.LocalDate;

public class Timeline {
  private int id;
  private String title;
  private String content;
  private Date event_date;
  private int group_id;
  private String username;
  private LocalDate eventDateLocal;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getEvent_date() {
    return event_date;
  }

  public void setEvent_date(Date event_date) {
    this.event_date = event_date;
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

  public LocalDate getEventDateLocal() {
    return eventDateLocal;
  }

  public void setEventDateLocal(LocalDate eventDateLocal) {
    this.eventDateLocal = eventDateLocal;
  }
}
