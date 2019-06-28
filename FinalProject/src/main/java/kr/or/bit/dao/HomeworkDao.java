package kr.or.bit.dao;

import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Homework;

public interface HomeworkDao extends OptionDao {
  
  void insertHomework(ArticleOption homework);
  
  void updateHomework(String filename);
  
  Homework selectAllHomeworkByArticleId(Article article);
}
