package kr.or.bit.model;

import java.security.Timestamp;
import java.time.LocalDateTime;

public class Message {
  private int id;
  private String content;
  private Timestamp time;
  private int read;
  private String sender_name;
  private String receiver_name;
  private LocalDateTime time_local;
  
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
  public int getRead() {
    return read;
  }
  public void setRead(int read) {
    this.read = read;
  }
  public String getSender_name() {
    return sender_name;
  }
  public void setSender_name(String sender_name) {
    this.sender_name = sender_name;
  }
  public String getReceiver_name() {
    return receiver_name;
  }
  public void setReceiver_name(String receiver_name) {
    this.receiver_name = receiver_name;
  }
  public LocalDateTime getTime_local() {
    return time_local;
  }
  public void setTime_local(LocalDateTime time_local) {
    this.time_local = time_local;
  }
}
