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

  int countAllQnaArticle(int course_id);
  
  int countQnaArticleByTitleOrContent(@Param("boardSearch") String boardSearch, @Param("course_id") int course_id);
  
  int countQnaArticleByTitle(@Param("boardSearch") String boardSearch,@Param("course_id") int course_id);
  
  int countQnaArticleByWriter(@Param("boardSearch") String boardSearch,@Param("course_id") int course_id);
  
  List<Article> selectQnaArticleByTitleOrContent(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch,@Param("course_id") int course_id);
  
  List<Article> selectQnaArticleByTitle(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch,@Param("course_id") int course_id);
  
  List<Article> selectQnaArticleByWriter(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch,@Param("course_id") int course_id);

  List<Article> selectQnaArticleByTag(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch,@Param("course_id") int course_id);

  List<Article> selectAllQnaArticle(@Param("pager")Pager pager, @Param("course_id") int course_id);
}