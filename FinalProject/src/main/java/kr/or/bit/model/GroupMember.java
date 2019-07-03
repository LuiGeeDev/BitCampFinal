package kr.or.bit.model;

public class GroupMember {
  private int group_id;
  private String member_name;

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

  public String getMember_name() {
    return member_name;
  }

  public void setMember_name(String member_name) {
    this.member_name = member_name;
  }

  @Override
  public String toString() {
    return "GroupMember [group_id=" + group_id + ", member_name=" + member_name + "]";
  }
}
