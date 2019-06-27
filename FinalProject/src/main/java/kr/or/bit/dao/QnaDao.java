package kr.or.bit.dao;

public interface QnaDao extends OptionDao {
  
  void insertQna(int articleid);
  
  void updateQnaByAnswered(int answered);
  
  void updateQnaByTeacherAnswered(int teacheranswered);
  
  void selectQnaByArticleId(int articleid);
}
