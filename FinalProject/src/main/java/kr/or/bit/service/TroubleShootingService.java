package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.model.Article;

public class TroubleShootingService {
  @Autowired
  private SqlSession sqlSession;
  
  public List<Article> getIssueOpened(int board_id, @RequestParam(defaultValue = "1", name = "p") int page,
      @RequestParam(required = false) String criteria, @RequestParam(required = false) String word) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    
    return null;
  }
  
  public List<Article> getIssueClosed() {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    
    return null;
  }
  
  public Article getIssue() {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    
    return null;
  }
  
  public void writeIssue() {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
  }
  
  public void closeIssue() {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
  }
}
