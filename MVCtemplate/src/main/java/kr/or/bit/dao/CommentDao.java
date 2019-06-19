package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Comment;

public interface CommentDao {
  List<Comment> getComments(int boardId);
  void writeComment(Comment comment);
}
