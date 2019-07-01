package kr.or.bit.dao;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Video;

public interface VideoDao {
  
  void insertVideo(ArticleOption video);
  
  void updateVideo(Video video);
  
  Video selectVideoByArticleId(@Param("article_id") int article_id);
  
  void deleteVideo(int id);
}
