package kr.or.bit.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;

@Service
public class ArticleVoteService {
  
  @Autowired
  private SqlSession sqlSession;
  
  public int insertVote(int articleId, String username) {
	  
	  ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
	  
	  int result = selectVote(articleId, username);
	  if(result>0) {
		  articleDao.deleteVote(articleId, username);
	  }else {
		  articleDao.insertVote(articleId, username);		  
	  }
	  
	  return articleDao.countVote(articleId);
  }
  
  public int selectVote(int articleId, String username) {
	  
	  ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
	  
	  return articleDao.selectVote(articleId, username);
  }

  
}
