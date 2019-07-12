package kr.or.bit.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Course;
import kr.or.bit.model.Member;
import kr.or.bit.model.Message;
import kr.or.bit.model.Project;
import kr.or.bit.utils.Helper;

@Controller
public class HomeController {
  private final int STACK_BOARD_ID = 1;
  private final int VIDEO_BOARD_ID = 2;
  
  @Autowired
  private SqlSession sqlSession;

  @GetMapping("/")
  public String home(Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);

    String username = Helper.userName();

    Member user = memberDao.selectMemberByUsername(username);
    List<Message> mainMessage = messageDao.selectMainMessage(username);
    List<Article> recentVideos = articleDao.selectAllArticleByBoardId(VIDEO_BOARD_ID);
    List<Article> recentStacks = articleDao.selectAllArticleByBoardId(STACK_BOARD_ID);
    List<Article> recentlyCommentedStacks = articleDao.selectRecentlyCommentedArticle();
    Project project = projectDao.selectRecentProject(user.getCourse_id());
    
    
    for (Article video : recentVideos) {
      video.setOption(videoDao.selectVideoByArticleId(video.getId()));
    }
    
    for (Article stack : recentStacks) {
      stack.setTimeLocal(stack.getTime().toLocalDateTime());
      stack.setWriter(memberDao.selectMemberByUsername(stack.getUsername()));
    }
    
    for (Article rcs : recentlyCommentedStacks) {
      rcs.setTimeLocal(rcs.getTime().toLocalDateTime());
      rcs.setWriter(memberDao.selectMemberByUsername(rcs.getUsername()));
    }
    
    for (Message m : mainMessage) {
      m.setTimeLocal(m.getTime().toLocalDateTime());
      m.setSenderName(memberDao.selectMemberByUsername(m.getSender_username()).getName());
    }
    
    project.setStartDateLocal(project.getStart_date().toLocalDate());
    project.setEndDateLocal(project.getEnd_date().toLocalDate());
    
    Period dDay = Period.between(LocalDate.now(), project.getEndDateLocal());

    model.addAttribute("user", user);
    model.addAttribute("mainMessage", mainMessage);
    model.addAttribute("recentVideos", recentVideos);
    model.addAttribute("recentStacks", recentStacks);
    model.addAttribute("recentlyCommented", recentlyCommentedStacks);
    model.addAttribute("project", project);
    model.addAttribute("dDay", dDay.getDays());
    return "main";
  }

  @GetMapping("/test")
  public String test(Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    
    String username = Helper.userName();
    
    List<Course> courses = courseDao.selectAllTeacherCourse(username);
    for (Course course : courses) {
      List<Board> boards = boardDao.selectBoardInCourse(course.getId(), 3);
      course.setBoards(boards);
    }
    
    model.addAttribute("courses", courses);
    return "test";
  }
  
  @PostMapping("/test/articles")
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
  
  @PostMapping("/test/more")
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
  
  @PostMapping("/test/article")
  public @ResponseBody Article getArticle(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Article article = articleDao.selectOneArticle(id);
    article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    article.setTimeLocal(article.getTime().toLocalDateTime());
    
    return article;
  }
  
  @PostMapping("/test/search")
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
  
  @GetMapping("/test/copy")
  public String copyArticle(@RequestParam(name = "ids", required=false) List<Integer> ids) {
    for (int id : ids) {
      System.out.println(id);
    }
    
    return "redirect:/test";
  }
}


