package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Homework;

public interface HomeworkDao extends OptionDao {
  
  void insertHomework(Homework homework);
  
  void updateHomework(String filename);
  
  Homework selectHomeworkByArticleId(int article_id);
  
  List<Homework> selectAllHomeworkArticle(int course_id);
}
