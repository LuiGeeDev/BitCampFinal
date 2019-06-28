package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
  @Autowired
  private SqlSession sqlSession;
  public void service() {
    System.out.println(sqlSession);
    System.out.println("Hi");
  }
}
