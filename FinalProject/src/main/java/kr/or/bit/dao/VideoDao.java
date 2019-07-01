package kr.or.bit.dao;

import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Video;

public interface VideoDao {
  
  void insertVideo(ArticleOption video);
  
  void updateVideo(ArticleOption video);
  
  Video selectVideoByArticleId(int article_id);
  
  void deleteVideo(int id);
}
