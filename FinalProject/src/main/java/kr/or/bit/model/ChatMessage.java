package kr.or.bit.model;

public class ChatMessage {
  private String username;
  private String name;
  private long time;
  private String content;
  private int group_id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "ChatMessage [username=" + username + ", time=" + time + ", content=" + content + ", group_id=" + group_id
        + "]";
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

}
