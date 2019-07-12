package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.Homework;
import kr.or.bit.utils.Pager;

public interface HomeworkDao extends OptionDao {
  
  void insertHomework(Homework homework);
  
  void updateHomework(String filename);
  
  Homework selectHomeworkByArticleId(int article_id);
  
  List<Article> selectAllHomeworkArticle(@Param("pager") Pager pager, @Param("course_id") int course_id);
  
  void updateHomeworkArticle(Article article);
  
  int countAllHomeworkArticle(int course_id);
  
  int countHomeworkArticleBySearchWorkd(@Param("course_id")int course_id, @Param("word") String word);
  
  List<Article> selectHomeworkArticleBySearchWord(@Param("pager") Pager pager, @Param("course_id") int coursed_id, @Param("word") String word);
  
  List<Article> selectRecentHomeworkArticle(@Param("course_id") int course_id);

}
