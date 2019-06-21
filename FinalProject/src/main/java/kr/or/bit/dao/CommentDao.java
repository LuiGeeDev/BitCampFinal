package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.ArticleOption;
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
  List<Comment> getComments(int boardId);
  void writeComment(Comment comment);

  public void insertComment(Comment comment, int id); // ArticleDTO의 id[글번호]
  
  public void updateComment(Comment comment, int articleId);
  
  public void deleteComment(int id);
  
  public void selectAllComment();
  
  public void insertVote(Member member, ArticleOption article);
  
  public void deleteVote(Member member, ArticleOption article);
  
}
