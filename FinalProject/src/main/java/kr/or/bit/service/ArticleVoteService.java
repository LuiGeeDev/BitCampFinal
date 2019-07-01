package kr.or.bit.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;

@Service
public class ArticleVoteService {
  
  @Autowired
  private SqlSession sqlSession;
  
  public Map<String,Object> insertVote(int articleId, String username) {
	  Map<String, Object> voteMap = new HashMap<String, Object>();
	  ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
	  
	  
	  int result = selectVote(articleId, username);
	  
	  if(result>0) {
		  articleDao.deleteVote(articleId, username);
		  voteMap.put("checkStatus", 0);//체크 했는지 안했는지
	  }else {
		  articleDao.insertVote(articleId, username);
		  voteMap.put("checkStatus", 1);
	  }
	  
	  voteMap.put("countVote",articleDao.countVote(articleId));
	  return voteMap;
  }
  
  public int selectVote(int articleId, String username) {
	  
	  ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
	  
	  return articleDao.selectVote(articleId, username);
  }

  
}
