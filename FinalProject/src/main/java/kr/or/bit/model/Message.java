package kr.or.bit.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message {
  private int id;
  private String content;
  private Timestamp time;
  private int checked;
  private String sender_username;
  private String receiver_username;

  private LocalDateTime timeLocal;
  
  private String senderName;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Timestamp getTime() {
    return time;
  }
  public void setTime(Timestamp time) {
    this.time = time;
  }
  public int getChecked() {
    return checked;
  }
  public void setChecked(int checked) {
    this.checked = checked;
  }
  public String getSender_username() {
    return sender_username;
  }
  public void setSender_username(String sender_username) {
    this.sender_username = sender_username;
  }
  public String getReceiver_username() {
    return receiver_username;
  }
  public void setReceiver_username(String receiver_username) {
    this.receiver_username = receiver_username;
  }
  public LocalDateTime getTimeLocal() {
    return timeLocal;
  }
  public void setTimeLocal(LocalDateTime timeLocal) {
    this.timeLocal = timeLocal;
  }
  public String getSenderName() {
    return senderName;
  }
  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

 
}
