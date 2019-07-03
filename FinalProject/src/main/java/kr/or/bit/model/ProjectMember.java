package kr.or.bit.model;

public class ProjectMember {
  private String username;
  private String name;
  private int group;
  private boolean leader;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGroup() {
    return group;
  }

  public void setGroup(int group) {
    this.group = group;
  }

  public boolean isLeader() {
    return leader;
  }

  public void setLeader(boolean leader) {
    this.leader = leader;
  }
}
