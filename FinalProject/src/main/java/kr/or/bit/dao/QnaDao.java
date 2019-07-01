package kr.or.bit.dao;

import kr.or.bit.model.Qna;

public interface QnaDao extends OptionDao {
  
  void insertQna(int article_id);
  
  void updateQnaByAnswered(int id);
  
  void updateQnaByTeacherAnswered(int id);
  
  Qna selectQnaByArticleId(int article_id);
}
