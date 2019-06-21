package kr.or.bit.model;

import java.util.List;

public class Group {
  private int id;
  private int group_no;
  private String link1;
  private String link2;
  private String link3;
  private String leader_name;
  private int project_id;
  
  private List<Member> groupMember;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getGroup_no() {
    return group_no;
  }

  public void setGroup_no(int group_no) {
    this.group_no = group_no;
  }

  public String getLink1() {
    return link1;
  }

  public void setLink1(String link1) {
    this.link1 = link1;
  }

  public String getLink2() {
    return link2;
  }

  public void setLink2(String link2) {
    this.link2 = link2;
  }

  public String getLink3() {
    return link3;
  }

  public void setLink3(String link3) {
    this.link3 = link3;
  }

  public String getLeader_name() {
    return leader_name;
  }

  public void setLeader_name(String leader_name) {
    this.leader_name = leader_name;
  }

  public int getProject_id() {
    return project_id;
  }

  public void setProject_id(int project_id) {
    this.project_id = project_id;
  }

  public List<Member> getGroupMember() {
    return groupMember;
  }

  public void setGroupMember(List<Member> groupMember) {
    this.groupMember = groupMember;
  }

  @Override
  public String toString() {
    return "Group [id=" + id + ", group_no=" + group_no + ", link1=" + link1 + ", link2=" + link2 + ", link3=" + link3
        + ", leader_name=" + leader_name + ", project_id=" + project_id + ", groupMember=" + groupMember + "]";
  }
}
