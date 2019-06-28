package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleVoteService {
  
  @Autowired
  private SqlSession sqlSession;
  
  

}
