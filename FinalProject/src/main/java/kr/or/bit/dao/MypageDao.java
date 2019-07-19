package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.utils.Pager;

/*
*
* @date: 2019. 7. 18.
* @author: 권예지
* @description: MypageDao
*
*/
public interface MypageDao {
  int countMyArticleByTitleOrContent(@Param("boardSearch") String boardSearch, @Param("username") String username);

  int countMyArticleByTitle(@Param("boardSearch") String boardSearch, @Param("username") String username);

  int countAllMyArticle(String username);
  
  List<Article> selectMyArticleByTitleOrContent(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch, @Param("username") String username);
  
  List<Article> selectMyArticleByTitle(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch, @Param("username") String username);
  
  List<Article> selectMyArticleByWriter(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch, @Param("username") String username);
  
  List<Article> selectEnableArticleByUsername2(@Param("pager") Pager pager, @Param("username") String username);
  
}
