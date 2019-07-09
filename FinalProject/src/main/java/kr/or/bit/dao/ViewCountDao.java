package kr.or.bit.dao;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.ViewCount;

public interface ViewCountDao {
  
  void insertViewCount(@Param("article_id") int article_id, @Param("username") String username);
  
  int countViewCountByArticleId(@Param("article_id") int article_id);
  
  int countExistViewCount(@Param("article_id") int article_id, @Param("username") String username);
}
