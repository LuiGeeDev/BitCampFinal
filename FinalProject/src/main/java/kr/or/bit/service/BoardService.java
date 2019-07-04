package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.model.Article;

@Service
public class BoardService {
  @Autowired
  private SqlSession sqlSession;

  public List<Article> getArticlesByPage(int boardId, int page) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    List<Article> articleList = articleDao.selectArticlesByPage(boardId, (page - 1) * 20, page * 20);
    return articleList;
  }

  @PostAuthorize("hasAnyRole('TEACHER', 'MANAGER') or returnObject.username == principal.username")
  public Article getArticleForUpdateOrDelete(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    Article article = articleDao.selectOneArticle(id);

    return article;
  }

  @PreAuthorize("hasAnyRole('TEACHER', 'MANAGER') or #article.username == principal.username")
  public void updateArticle(Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleDao.updateArticle(article);
  }

  @PreAuthorize("hasAnyRole('TEACHER', 'MANAGER') or #article.username == principal.username")
  public void deleteArticle(Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(article.getId());
  }
}