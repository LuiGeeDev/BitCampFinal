package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.model.Article;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Service
public class TroubleShootingService {
  @Autowired
  private SqlSession sqlSession;
  
  public Pager getPager(int board_id, int page, String criteria, String word, boolean closed) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    int totalArticles = closed ? articleDao.selectAllIssuesClosed(board_id, criteria, word).size() : articleDao.selectAllIssuesOpened(board_id, criteria, word).size();
    Pager pager = new Pager(page, totalArticles);
    return pager;
  }
  
  public List<Article> getIssueOpened(int board_id, int page, String criteria, String word) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    Pager pager = new Pager(page, articleDao.selectAllIssuesOpened(board_id, criteria, word).size()); 
    List<Article> articleList = articleDao.selectIssuesOpenedByPage(board_id, pager, criteria, word);
    for (Article article : articleList) {
      article.setCommentlist(commentDao.selectAllComment(article.getId()));
      article.setOption(troubleShootingDao.selectTroubleShootingByArticleId(article.getId()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    }
    
    return articleList;
  }
  
  public List<Article> getIssueClosed(int board_id, @RequestParam(defaultValue = "1", name = "p") int page,
      @RequestParam(required = false) String criteria, @RequestParam(required = false) String word) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    
    Pager pager = new Pager(page, articleDao.selectAllIssuesClosed(board_id, criteria, word).size()); 
    List<Article> articleList = articleDao.selectIssuesClosedByPage(board_id, pager, criteria, word);
    for (Article article : articleList) {
      article.setCommentlist(commentDao.selectAllComment(article.getId()));
      article.setOption(troubleShootingDao.selectTroubleShootingByArticleId(article.getId()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }
    
    return articleList;
  }
  
  public Article getIssue(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    
    Article article = articleDao.selectOneArticle(id);
    article.setCommentlist(commentDao.selectAllComment(id));
    article.setOption(troubleShootingDao.selectTroubleShootingByArticleId(id));
    article.setTimeLocal(article.getTime().toLocalDateTime());
    
    return article;
  }
  
  public int writeIssue(Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    String username = Helper.userName();
    article.setUsername(username);
    articleDao.insertArticle(article);
    return articleDao.getMostRecentArticleId();
  }
  
  public void changeIssueStatus(int id) {
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    troubleShootingDao.changeTroubleShootingStatus(id);
  }
}
