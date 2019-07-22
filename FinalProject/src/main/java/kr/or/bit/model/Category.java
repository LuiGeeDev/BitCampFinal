package kr.or.bit.model;

public class Category {
  private String category;
  private int id;
  private int course_id;
  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getCourse_id() {
    return course_id;
  }
  public void setCourse_id(int course_id) {
    this.course_id = course_id;
  }
  @Override
  public String toString() {
    return "Category [category=" + category + ", id=" + id + ", course_id=" + course_id + "]";
  }

}
