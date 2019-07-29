package kr.or.bit.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MypageDao;
import kr.or.bit.dao.ScrapDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Course;
import kr.or.bit.model.Member;
import kr.or.bit.utils.Pager;

@Service
public class MypageService {
  @Autowired
  private SqlSession sqlSession;

  public List<Article> allArticleByUsername(String username) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    List<Article> articleList = articleDao.selectEnableArticleByUsername(username);
    for (Article article : articleList) {
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }
    return articleList;
  }

  public List<Article> selectAllMyArticlesByUsername(Pager pager, String username) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MypageDao mypageDao = sqlSession.getMapper(MypageDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    List<Article> articleList = mypageDao.selectEnableArticleByUsername2(pager, username);
    for (Article article : articleList) {
      article.setTimeLocal(article.getTime().toLocalDateTime());
      List<Comment> commentList = commentdao.selectAllComment(article.getId());
      article.setCommentlist(commentList);
      article.setBoardtype(boardDao.selectBoardById(article.getBoard_id()).getBoardtype());
    }

    return articleList;
  }

  public String selectOneArticleforMypage(int article_id) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Article article = articledao.selectOneArticle(article_id);
    Board board = boardDao.selectBoardById(article.getBoard_id());
    int board_type = board.getBoardtype();
    int board_id = article.getBoard_id();
    String returnURL = "";
    switch (board_type) {
    case 1: // 스택 게시판
      returnURL = "http://localhost:8090/stack/content?id=" + article_id;
      break;
    case 2: // 동영상 게시판
      returnURL = "http://localhost:8090/video/detail?id=" + article_id;
      break;
    case 3: // 일반 게시판
      returnURL = "http://localhost:8090/myclass/board/read?article_id=" + article_id + "&board_id=" + board_id;
      break;
    case 5: // 질문 게시판
      returnURL = "http://localhost:8090/myclass/qna/content?id=" + article_id;
      break;
    }

    return "redirect:" + returnURL;
  }

  public int scrapCount(int articleId, String username) {
    ScrapDao scrapDao = sqlSession.getMapper(ScrapDao.class);
    return scrapDao.selectOneScrapCount(articleId, username);
  }

  public Map<String, Object> insertBookmark(int article_id, String username) {
    Map<String, Object> Scrap = new HashMap<String, Object>();
    ScrapDao scrapDao = sqlSession.getMapper(ScrapDao.class);
    int result = scrapDao.selectOneScrapCount(article_id, username);
    if (result > 0) {
      scrapDao.deleteScrap(article_id, username);
      Scrap.put("checkStatus", 0);//체크 했는지 안했는지
    } else {
      scrapDao.insertScrap(article_id, username);
      Scrap.put("checkStatus", 1);
    }

    Scrap.put("countScrap", scrapDao.selectOneScrapTotalCount(article_id));
    return Scrap;
  }

  public int coursePeriod(String username) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    Member user = memberDao.selectMemberByUsername(username);
    Course course = courseDao.selectCourse(user.getCourse_id());
    course.setEndDate(course.getEnd_date().toLocalDate());
    course.setStartDate(course.getStart_date().toLocalDate());
    long classPeriod = ChronoUnit.DAYS.between(course.getStartDate(), course.getEndDate());
    long daysFromStart = ChronoUnit.DAYS.between(course.getStartDate(), LocalDate.now());
    int completion = (int) Math.floor((float) daysFromStart / classPeriod * 100);

    return completion;
  }

  public List<Article> selectMyArticlesByboardSearch(Pager pager, String boardSearch, String criteria,
      String username) {
    MypageDao mypageDao = sqlSession.getMapper(MypageDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Article> articleList = null;
    if (criteria.equals("titleOrContent")) {
      articleList = mypageDao.selectMyArticleByTitleOrContent(pager, boardSearch, username);
    } else {
      articleList = mypageDao.selectMyArticleByTitle(pager, boardSearch, username);
    }

    for (Article article : articleList) {
      List<Comment> commentList = commentdao.selectAllComment(article.getId());
      article.setCommentlist(commentList);
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    }
    return articleList;
  }

  public List<Comment> selectMyCommentsByboardSearch(String boardSearch, String criteria, String username) {
    MypageDao mypageDao = sqlSession.getMapper(MypageDao.class);
    List<Comment> commentList = null;
    if (criteria.equals("Commentcontent")) {
      commentList = mypageDao.selectMyCommentByContent(boardSearch, username);
    }
    for (Comment comment : commentList) {
      comment.setTimeLocal(comment.getTime().toLocalDateTime());
    }
    return commentList;
  }

}
