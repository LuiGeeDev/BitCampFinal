package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.model.Comment;

@Service
public class CommentService {
  
  @Autowired
  private SqlSession sqlSession;
  
  public List<Comment> selectAllComment(int article_id) {
    
  }
  
}
