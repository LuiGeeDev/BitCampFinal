package kr.or.bit.model;

public class Notification {
  private int id;
  private String content;
  private int checked;
  private String username;

  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

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

  public int getChecked() {
    return checked;
  }

  public void setChecked(int checked) {
    this.checked = checked;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "Alram [id=" + id + ", content=" + content + ", checked=" + checked + ", username=" + username + "]";
  }
}
