package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Member;

/*
*
* @date: 2019. 06. 21.
*
* @author: 권예지
*
* @description: CommentDao를 통해서 Comment CRUD를 이용한다
* 
*/
public interface CommentDao { 
  
  List<Comment> selectAllComment(int article_id);
  
  Comment selectComment(@Param("article_id") int article_id, @Param("id") int id);
  
  Comment selectOneComment(int id);
  
  void insertComment(Comment comment); // ArticleDTO의 id[글번호]

  void updateComment(Comment comment);

  void deleteComment(int id);

  void insertVote(@Param("username") String username, @Param("id") int id);

  void deleteVote(@Param("username") String username, @Param("id") int id);
  
  void countVote(int id);
}
