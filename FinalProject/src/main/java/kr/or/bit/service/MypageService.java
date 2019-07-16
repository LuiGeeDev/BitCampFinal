package kr.or.bit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Board;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;
import kr.or.bit.model.Member;
import kr.or.bit.model.Tag;


@Service
public class MypageService {
  
  @Autowired
  private SqlSession sqlSession;
  
  public List<Article> allArticleByUsername(String username){
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    List<Article> articleList = articleDao.selectEnableArticleByUsername(username);
    for (Article article : articleList) {
      article.setTimeLocal(article.getTime().toLocalDateTime());
      //article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
    }  
    return articleList;
  }
  
    public String selectOneArticleforMypage(int article_id) {
      ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
      MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
      
      Article article = articledao.selectOneArticle(article_id);
      Board board = boardDao.selectBoardById(article.getBoard_id());
      Member member = memberDao.selectMemberByUsername(article.getUsername());     
      
      int board_type = board.getBoardtype();
      int board_id = article.getBoard_id();
      String returnURL = "";
      
      System.out.println("board_type:"+board_type);
           
      switch(board_type) {
      case 1: //스택 게시판
        returnURL = "stack/content?id=" + article_id;
        break;
      case 2: //동영상 게시판
        returnURL = "video/detail?id=" + article_id;
        break;
      case 3: //일반 게시판
        returnURL = "myclass/board/read?article_id=" + article_id + "&board_id=" + board_id; 
        break;
      case 4: //과제제출  
        returnURL = "myclass/homework/detail?id=" + article_id;
        break;
      case 5: //질문 게시판
        returnURL = "myclass/qna/content?id=" + article_id;
        break;
      case 6: //트러블슈팅 게시판
        //이건 어떡할까 하하핳~~~
      }
      
      System.out.println("returnURL:"+returnURL);

      return "redirect:"+ returnURL;
    }
  
  
}
