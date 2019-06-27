package kr.or.bit.dao;

import kr.or.bit.model.Article;

public interface QnaDao extends OptionDao {
  
  void insertQna(Article article);
  
  void updateQnaByAnswered(String qna, int id);
  
  void updateQnaByTeacherAnswered(String qnat, int id);
  
  void selectQnaByArticleId(Article article);
}
