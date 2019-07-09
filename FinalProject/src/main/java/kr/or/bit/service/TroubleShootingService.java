package kr.or.bit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Member;
import kr.or.bit.model.Tag;
import kr.or.bit.model.TroubleShooting;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Service
public class TroubleShootingService {
  @Autowired
  private SqlSession sqlSession;
  
  @Autowired
  private TagService tagService;
  
  public Pager getPager(int board_id, int page, String criteria, String word, boolean closed) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    int totalArticles = closed ? articleDao.selectAllIssuesClosed(board_id, criteria, word).size() : articleDao.selectAllIssuesOpened(board_id, criteria, word).size();
    Pager pager = new Pager(page, totalArticles);
    return pager;
  }
  
  public int numberOfIssueOpened(int board_id, String criteria, String word) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    return articleDao.selectAllIssuesOpened(board_id, criteria, word).size();
  }
  
  public int numberOfIssueClosed(int board_id, String criteria, String word) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    return articleDao.selectAllIssuesClosed(board_id, criteria, word).size();
  }
  
  public List<Article> getIssueOpened(int board_id, int page, String criteria, String word) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    StackDao stackDao = sqlSession.getMapper(StackDao.class);
    
    Pager pager = new Pager(page, articleDao.selectAllIssuesOpened(board_id, criteria, word).size()); 
    List<Article> articleList = articleDao.selectIssuesOpenedByPage(board_id, pager, criteria, word);
    for (Article article : articleList) {
      article.setCommentlist(commentDao.selectAllComment(article.getId()));
      article.setOption(troubleShootingDao.selectTroubleShootingByArticleId(article.getId()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTags(stackDao.selectTagList(article.getId()));
    }
    
    return articleList;
  }
  
  public List<Article> getIssueClosed(int board_id, int page, String criteria, String word) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    StackDao stackDao = sqlSession.getMapper(StackDao.class);
    
    Pager pager = new Pager(page, articleDao.selectAllIssuesClosed(board_id, criteria, word).size()); 
    List<Article> articleList = articleDao.selectIssuesClosedByPage(board_id, pager, criteria, word);
    for (Article article : articleList) {
      article.setCommentlist(commentDao.selectAllComment(article.getId()));
      article.setOption(troubleShootingDao.selectTroubleShootingByArticleId(article.getId()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTags(stackDao.selectTagList(article.getId()));
    }
    
    return articleList;
  }
  
  public Article getIssue(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    StackDao stackDao = sqlSession.getMapper(StackDao.class);
    
    Article article = articleDao.selectOneArticle(id);
    List<Comment> commentlist = commentDao.selectAllComment(id);
    for (Comment comment : commentlist) {
      comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      comment.setTimeLocal(comment.getTime().toLocalDateTime());
    }
    article.setCommentlist(commentlist);
    article.setOption(troubleShootingDao.selectTroubleShootingByArticleId(id));
    article.setTimeLocal(article.getTime().toLocalDateTime());
    article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    article.setTags(stackDao.selectTagList(article.getId()));
    
    return article;
  }
  
  @Transactional
  public int writeIssue(Article article, int group_id, String tag) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    List<String> tagList = new ArrayList<>();
    String[] splitStr = tag.split("#");
    for (int i = 1; i < splitStr.length; i++) {
      tagList.add(splitStr[i]);
    }
    List<Tag> tags = tagService.selectTagByName(tagList);
    
    article.setTags(tags);
    article.setUsername(Helper.userName());
    articleDao.insertArticle(article);
    tagService.insertTag(tagList, articleDao.getMostRecentArticleId());
    
    TroubleShooting troubleshooting = new TroubleShooting();
    troubleshooting.setArticle_id(articleDao.getMostRecentArticleId());
    troubleshooting.setGroup_id(group_id);
    troubleShootingDao.insertTroubleShooting(troubleshooting);
    return articleDao.getMostRecentArticleId();
  }
  
  public void changeIssueStatus(int id) {
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    troubleShootingDao.changeTroubleShootingStatus(id);
  }
  
  public void writeComment(Comment comment) {
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    comment.setUsername(Helper.userName());
    commentDao.insertComment(comment);
  }
}
