package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;

/*
*
* @date: 2019. 7. 17.
*
* @author: 권예지
*
* @description: ScrapDao
*
*/
public interface ScrapDao {
  
  void insertScrap(@Param("article_id") int article_id, @Param("username") String username);
  
  List<Article> selectAllScrap(String username);
  
}
