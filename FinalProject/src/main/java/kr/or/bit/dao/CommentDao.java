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
  void insertComment(Comment comment, int id); // ArticleDTO의 id[글번호]

  void updateComment(Comment comment, int articleId);

  void deleteComment(int id);

  void insertVote(Member member, ArticleOption article);

  void deleteVote(Member member, ArticleOption article);
}
