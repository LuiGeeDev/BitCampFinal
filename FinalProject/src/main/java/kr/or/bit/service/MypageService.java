package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.model.Article;


@Service
public class MypageService {
  
  @Autowired
  private SqlSession sqlSession;
  
  public List<Article> allArticleByUsername(String username){
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    List<Article> articleList = articleDao.selectEnableArticleByUsername(username);
    for (Article article : articleList) {
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
    }  
    return articleList;
  }
  
}
