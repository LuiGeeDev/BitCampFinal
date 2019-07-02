package kr.or.bit.model;

public class Files {
  private int id;
  private String original_filename;
  private String filename;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOriginal_filename() {
    return original_filename;
  }

  public void setOriginal_filename(String original_filename) {
    this.original_filename = original_filename;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  @Override
  public String toString() {
    return "File [id=" + id + ", original_filename=" + original_filename + ", filename=" + filename + "]";
  }
}
