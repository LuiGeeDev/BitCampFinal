package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.model.Article;


@Service
public class ArticleUpdateService implements ArticleOri {
  @Autowired
  private SqlSession sqlSession;
  
  public void updateArticle(Article article) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    
    articledao.updateArticle(article);
  }
}
