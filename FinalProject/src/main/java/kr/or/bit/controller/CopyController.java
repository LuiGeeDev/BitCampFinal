package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.General;
import kr.or.bit.utils.Helper;

@Controller
public class CopyController {
  @Autowired
  private SqlSession sqlSession;
  
  @PostMapping("/copy/articles")
  public @ResponseBody List<Article> getArticles(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Article> articles = articleDao.selectFirstArticlesToCopyByBoardId(id);
    for (Article article : articles) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }
    
    return articles;
  }
  
  @PostMapping("/copy/more")
  public @ResponseBody List<Article> getMoreArticles(int board_id, int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Article> articles = articleDao.selectNextArticlesToCopyByBoardId(board_id, id);
    for (Article article : articles) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }
    
    return articles;
  }
  
  @PostMapping("/copy/article")
  public @ResponseBody Article getArticle(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Article article = articleDao.selectOneArticle(id);
    article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    article.setTimeLocal(article.getTime().toLocalDateTime());
    
    return article;
  }
  
  @PostMapping("/copy/search")
  public @ResponseBody List<Article> searchArticle(int board_id, String title){
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    List<Article> searchArticleList = articleDao.selectSearchTitleByBoardId(board_id, title);
    for (Article article : searchArticleList) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }
    return searchArticleList;
  }
  
  @GetMapping("/copy/copy")
  @Transactional
  public String copyArticle(int board_id, @RequestParam(name = "ids", required=false) List<Integer> ids) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    
    for (int id : ids) {
      Article oldArticle = articleDao.selectOneArticle(id);
      oldArticle.setUsername(Helper.userName());
      oldArticle.setBoard_id(board_id);
      articleDao.insertArticle(oldArticle);
      
      General general = generalDao.selectGeneralByArticleId(id);
      int newId = articleDao.getMostRecentArticleId();
      general.setArticle_id(newId);
      generalDao.insertGeneral(general);
    }
    
    return "redirect:/myclass/board?board_id=" + board_id;
  }
}
