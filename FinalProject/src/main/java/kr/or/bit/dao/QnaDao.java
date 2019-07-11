package kr.or.bit.dao;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Qna;

public interface QnaDao extends OptionDao {
  void insertQna(int article_id);

  void updateQnaByAnswered(int id);

  void updateQnaByTeacherAnswered(int id);

  Qna selectQnaByArticleId(int article_id);

  void chooseAnswer(@Param("comment_id") int comment_id, @Param("article_id") int article_id);

  
}