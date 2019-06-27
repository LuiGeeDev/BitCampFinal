package kr.or.bit.dao;

import kr.or.bit.model.ArticleOption;

public interface QnaDao extends OptionDao {
  
  void insertQna(int articleid);
  
  void updateQnaByAnswered(int answered);
  
  void updateQnaByTeacherAnswered(int teacheranswered);
  
  void deleteQna(int id);
  
  void selectQnaByArticleId(int articleid);
}
