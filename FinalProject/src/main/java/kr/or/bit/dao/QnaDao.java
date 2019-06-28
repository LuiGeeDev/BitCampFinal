package kr.or.bit.dao;

import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;

public interface QnaDao extends OptionDao {
  
  void insertQna(int id);
  
  void updateQnaByAnswered();
  
  void updateQnaByTeacherAnswered();
  
  void selectQnaByArticleId(int id);
}
