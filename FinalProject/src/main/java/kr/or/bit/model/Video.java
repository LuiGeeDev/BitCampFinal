package kr.or.bit.model;

public class Video implements ArticleOption {
  
  private int id;
  private String video_id;
  private int article_id;
  
  private int vote_count;

public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getVideo_id() {
    return video_id;
  }
  public void setVideo_id(String video_id) {
    this.video_id = video_id;
  }
  public int getArticle_id() {
    return article_id;
  }
  public void setArticle_id(int article_id) {
    this.article_id = article_id;
  }
public int getVote_count() {
	return vote_count;
}
public void setVote_count(int vote_count) {
	this.vote_count = vote_count;
}
@Override
public String toString() {
	return "Video [id=" + id + ", video_id=" + video_id + ", article_id=" + article_id + ", vote_count=" + vote_count
			+ "]";
}


}
