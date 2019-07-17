package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.Scrap;

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
  
  //게시판에서 스크랩버튼 눌렀을때 쓰는 메소드
  void insertScrap(@Param("article_id") int article_id, @Param("username") String username);
  
  //스크랩했나 안했나 확인하는 용도임1
  Scrap selectOneScrap(@Param("article_id") int article_id, @Param("username") String username);
  
  //스크랩했나 안했나 확인하는 용도임 2
  int selectOneScrapCount(@Param("article_id") int article_id, @Param("username") String username);
  
  //마이페이지 스크랩목록 불러올때 쓰는 메소드
  List<Article> selectAllScrap(String username);
  
  //마이페이지 스크랩목록에서 삭제하고싶을때 쓰세요
  void deleteScrap(int article_id);
  
  
}
