package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.CommentDao;
import kr.or.bit.model.Comment;

@Service
public class CommentService {
  
  @Autowired
  private SqlSession sqlSession;
  
  public List<Comment> selectAllComment(int article_id) {
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    List<Comment> list = commentdao.selectAllComment(article_id);    
    return list;
  }
  
  public Comment selectComment(int article_id, int id) {
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    Comment comment = commentdao.selectComment(article_id, id);
    return comment;
  }
  
  public void insertComment(Comment comment) {
    
  }
  
  public void updateComment(Comment comment) {
    
  }
  
  public void deleteComment(Comment comment) {
    
  }
}
