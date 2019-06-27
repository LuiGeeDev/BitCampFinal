package kr.or.bit.dao;

import java.util.List;

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
  
  Comment selectComment(int id);
  
  void insertComment(Comment comment); // ArticleDTO의 id[글번호]

  void updateComment(Comment comment);

  void deleteComment(int id);

  void insertVote(String username, int id);

  void deleteVote(String username, int id);
}
