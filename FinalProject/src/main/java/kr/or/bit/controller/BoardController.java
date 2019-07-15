package kr.or.bit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Course;
import kr.or.bit.model.General;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.BoardService;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Controller
@RequestMapping("/myclass/board")
public class BoardController {
  @Autowired
  private BoardService boardService;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private SqlSession sqlSession;

  /*
   * @GetMapping("") public String listPage(int
   * board_id, @RequestParam(defaultValue = "1") int page,
   * 
   * @RequestParam(required = false) String sort, @RequestParam(required = false)
   * String search,
   * 
   * @RequestParam(required = false) String criteria, Model model) { List<Article>
   * articles = null; if (sort == null && search == null) { articles =
   * boardService.getArticlesByPage(board_id, page); } else if (sort != null) {
   * articles = boardService.getArticlesSorted(board_id, page, sort); } else if
   * (search != null) { articles = boardService.getArticlesBySearchWord(board_id,
   * page, search, criteria); } model.addAttribute("board",
   * boardService.getBoardInfo(board_id)); model.addAttribute("articles",
   * articles); return "myclass/general/generalBoard"; }
   */
  @GetMapping("")
  public String listPage(int board_id, @RequestParam(defaultValue = "1") int page,
      @RequestParam(required = false) String boardSearch, @RequestParam(required = false) String criteria,
      Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    List<Article> articles = null;
    Pager pager = null;
    if (boardSearch != null) {
      if (criteria.equals("titleOrContent")) {
        pager = new Pager(page, generalDao.countAllGeneralArticlesByBoardIdAndTitleOrContent(board_id, boardSearch));
      } else if (criteria.equals("title")) {
        pager = new Pager(page, generalDao.countAllGeneralArticlesByBoardIdAndTitle(board_id, boardSearch));
      } else {
        pager = new Pager(page, generalDao.countAllGeneralArticlesByBoardIdAndWriter(board_id, boardSearch));
      }
      articles = articleService.selectAllArticlesByBoardSearch(board_id, pager, boardSearch, criteria);
      model.addAttribute("boardSearch", boardSearch);
    } else {
      pager = new Pager(page, generalDao.countAllGeneralArticlesByBoardId(board_id));
      articles = articleService.selectAllArticle("general", board_id, pager);
    }
    
    List<Course> courses = courseDao.selectAllTeacherCourse(Helper.userName());
    for (Course course : courses) {
      List<Board> boards = boardDao.selectBoardInCourse(course.getId(), 3);
      course.setBoards(boards);
    }
    
    model.addAttribute("articles", articles);
    model.addAttribute("pager", pager);
    model.addAttribute("page", page);
    model.addAttribute("board", boardService.getBoardInfo(board_id));
    model.addAttribute("courses", courses);
    return "myclass/general/generalBoard";
  }

  @GetMapping("/write")
  public String writePage(int board_id, Model model) {
    model.addAttribute("board", boardService.getBoardInfo(board_id));
    return "myclass/general/generalBoardWrite";
  }

  @PostMapping("/write")
  public String writeArticle(Article article, MultipartFile file1, MultipartFile file2, HttpServletRequest request) {
    return "redirect:/myclass/board/read?article_id=" + boardService.writeArticle(article, file1, file2, request)
        + "&board_id=" + article.getBoard_id();
  }

  @GetMapping("/read")
  public String readArticle(int article_id, int board_id, Model model) {
    Article article = boardService.readArticle(article_id);
    model.addAttribute("article", article);
    model.addAttribute("board", boardService.getBoardInfo(board_id));
    return "myclass/general/generalBoardDetail";
  }

  @GetMapping("/edit")
  public String updatePage(int article_id, int board_id, Model model) {
    Article article = boardService.getArticleForUpdateOrDelete(article_id);
    model.addAttribute("article", article);
    model.addAttribute("board", boardService.getBoardInfo(board_id));
    return "myclass/general/generalEdit";
  }

  @PostMapping("/edit")
  public String updateArticle(Article article, MultipartFile file1, MultipartFile file2, int board_id,
      HttpServletRequest request) throws IllegalStateException, IOException {
    boardService.updateArticle(article, file1, file2, request);
    return "redirect:/myclass/board/read?article_id=" + article.getId() + "&board_id=" + article.getBoard_id();
  }

  @GetMapping("/delete")
  public String deleteArticle(int article_id) {
    Article article = boardService.getArticleForUpdateOrDelete(article_id);
    boardService.deleteArticle(article);
    return "redirect:/myclass/board?board_id=" + article.getBoard_id();
  }

  @PostMapping("/commentwrite")
  public @ResponseBody List<Comment> commentWrite(int article_id, Comment comment) {
    boardService.writeComment(article_id, comment);
    List<Comment> commentList = boardService.getCommentList(article_id);
    return commentList;
  }

  @GetMapping("/commentdelete")
  public String commentDelete(int article_id, int board_id, int comment_id) {
    boardService.deleteComment(comment_id);
    return "redirect:/myclass/board/read?article_id=" + article_id + "&board_id=" + board_id;
  }

  @PostMapping("/deletefile1")
  public String fileDelete1(int count1, Article article) {
    System.out.println(count1);
    System.out.println(article);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    General general = new General();
    general.setArticle_id(article.getId());
    generalDao.updateFileOneGeneral(general);
    return "redirect:/myclass/board/edit?article_id=" + article.getId() + "&board_id=" + article.getBoard_id();
  }

  @PostMapping("/deletefile2")
  public String fileDelete2(int count2, Article article) {
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    General general = new General();
    general.setArticle_id(article.getId());
    generalDao.updateFileTwoGeneral(general);
    return "redirect:/myclass/board/edit?article_id=" + article.getId() + "&board_id=" + article.getBoard_id();
  }

  @GetMapping("/replyWrite")
  public String replyWrite(Model model, int board_id, int article_id) {
    model.addAttribute("article", articleService.selectOneArticle("general", article_id));
    model.addAttribute("board", boardService.getBoardInfo(board_id));
    System.out.println(articleService.selectOneArticle("general", article_id));
    return "myclass/general/generalBoardrWriteReply";
  }

  @PostMapping("/replyWrite")
  public String replyWrite(Article article, MultipartFile file1, MultipartFile file2, HttpServletRequest request) {
    System.out.println("reply start" + article);
    ArticleDao arti = sqlSession.getMapper(ArticleDao.class);
    article.setLevel(article.getLevel() + 1);
    article.setLayer(article.getLayer() + 1);
    article.setSibling(article.getSibling() + 1);
    
    if (article.getLayer() == 1) {
      int maxlevel = arti.selectMaxLevel(article);
      System.out.println(maxlevel);
      article.setLevel(maxlevel + 1);
    } else if (article.getLayer() > 1) {
      int maxlevelbysibling = 0;
      if( arti.selectMaxLevelBySibling(article) == null) {
        maxlevelbysibling = article.getLevel();
      } else {
        maxlevelbysibling = arti.selectMaxLevelBySibling(article);
      }
      System.out.println("maxlevel" +maxlevelbysibling );
      arti.updateArticleLevel(article);
      article.setLevel(maxlevelbysibling);
      
    }
    return "redirect:/myclass/board/read?article_id=" + boardService.writeReplyArticle(article, file1, file2, request)
        + "&board_id=" + article.getBoard_id();
  }
}
