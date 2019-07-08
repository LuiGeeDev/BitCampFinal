package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.Criteria;
import kr.or.bit.model.Homework;

public interface HomeworkDao extends OptionDao {
  
  void insertHomework(Homework homework);
  
  void updateHomework(String filename);
  
  Homework selectHomeworkByArticleId(int article_id);
  
  List<Article> selectAllHomeworkArticle(@Param("cri") Criteria cri, @Param("course_id") int course_id);
      
  void updateHomeworkArticle(Article article);
  
  int countAllHomeworkArticle(int course_id);
}
