package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.Qna;
import kr.or.bit.utils.Pager;

public interface QnaDao extends OptionDao {
  void insertQna(int article_id);

  void updateQnaByAnswered(int id);

  void updateQnaByTeacherAnswered(int id);

  Qna selectQnaByArticleId(int article_id);

  void chooseAnswer(@Param("comment_id") int comment_id, @Param("article_id") int article_id);

  int countAllQnaArticle();
  
  int countQnaArticleByTitleOrContent(@Param("boardSearch") String boardSearch);
  
  int countQnaArticleByTitle(@Param("boardSearch") String boardSearch);
  
  int countQnaArticleByWriter(@Param("boardSearch") String boardSearch);
  
  List<Article> selectQnaArticleByTitleOrContent(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectQnaArticleByTitle(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectQnaArticleByWriter(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);

  List<Article> selectQnaArticleByTag(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);

  List<Article> selectAllQnaArticle(Pager pager);
}