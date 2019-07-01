package kr.or.bit.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.ArticleOptionDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Comment;
import kr.or.bit.model.General;
import kr.or.bit.model.Homework;
import kr.or.bit.model.Qna;
import kr.or.bit.model.TroubleShooting;
import kr.or.bit.model.Video;

@Service
public class ArticleService {
  @Autowired
  private SqlSession sqlSession;
  
  public void updateArticle() {
    
  }
  
  public List<Article> selectArticlesOnNextPage(int article_id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
    
    List<Article> articleList = articleDao.selectArticlesOnNextPage(article_id);
    
    for (Article article : articleList) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setCommentlist(commentdao.selectAllComment(article.getId()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
      article.setOption(videoDao.selectVideoByArticleId(article.getId())); 
    }
    
    return articleList;
  }

  public List<Article> selectAllArticle(String optionName, int boardId) {//게시판아이디를 기준으로 게시글을 전부 불러오는 함수
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    List<Article> list = articledao.selectAllArticleByBoardId(boardId);
    
    for (Article article : list) {
      ArticleOption option = null;
      switch (optionName.toLowerCase()) {
        case "video":
          VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
          option = videoDao.selectVideoByArticleId(article.getId()); 
          break;
        case "troubleshooting":
          TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
          option = troubleShootingDao.selectTroubleShootingByArticleId(article.getId());
          break;
        case "general":
          GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
          option = generalDao.selectGeneralByArticleId(article.getId());
          break;
        case "qna":
          QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
          option = qnaDao.selectQnaByArticleId(article.getId());
          break;
        case "homework":
          HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
          option = homeworkDao.selectHomeworkByArticleId(article.getId());
          break;
        default:
          break;
      }
      
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
      article.setCommentlist(commentdao.selectAllComment(article.getId()));
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setOption(option);
    }
    
    return list; 
  }
  
  public Article selectOneArticle(String optionName, int id) {
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    Article article = articledao.selectOneArticle(id);
    
    ArticleOption option = null;
    switch (optionName.toLowerCase()) {
      case "video":
        VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
        option = videoDao.selectVideoByArticleId(article.getId()); 
        break;
      case "troubleshooting":
        TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
        option = troubleShootingDao.selectTroubleShootingByArticleId(article.getId());
        break;
      case "general":
        GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
        option = generalDao.selectGeneralByArticleId(article.getId());
        break;
      case "qna":
        QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
        option = qnaDao.selectQnaByArticleId(article.getId());
        break;
      case "homework":
        HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
        option = homeworkDao.selectHomeworkByArticleId(article.getId());
        break;
      default:
        break;
    }
    
    article.setTimeLocal(article.getTime().toLocalDateTime());
    article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
    article.setCommentlist(commentdao.selectAllComment(id));
    article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    article.setOption(option);
    return article;
  } 
}
