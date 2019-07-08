package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.FilesDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.CommentService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/general")
public class GeneralBoardController {
  private final int GENERAL_BOARD_ID = 23;
  @Autowired
  private SqlSession sqlsession;
  @Autowired
  private ArticleInsertService articleInsertService;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;

  @GetMapping("/generalBoard")
  public String generalBoard(Model model) {
    List<Article> article = articleService.selectAllArticle("general", GENERAL_BOARD_ID);
    model.addAttribute("list", article);
    return "myclass/general/generalBoard";
  }

  @GetMapping("/detail")
  public String generalBoardDetail(int id, Model model) {
    String optionName = "general";
    Article article = articleService.selectOneArticle(optionName, id);
    model.addAttribute("list", article);
    return "myclass/general/generalBoardDetail";
  }

  @GetMapping("/generalBoardWrite")
  public String generalBoardWriteForm() {
    return "myclass/general/generalBoardWrite";
  }

  @PostMapping("/generalBoardWrite")
  public String generalBoardWrite(Article article, MultipartFile file1, MultipartFile file2,
      HttpServletRequest request) {
    article.setUsername(Helper.userName());
    article.setBoard_id(GENERAL_BOARD_ID);
    List<MultipartFile> list = new ArrayList<>();
    list.add(file1);
    list.add(file2);
    System.out.println("list : " + list.toString());
    General general = new General();
    articleInsertService.writeArticle(article, general, list, request);
    return "redirect:/general/generalBoard";
  }

  @GetMapping("/edit")
  public String generalEditForm(int id, Model model) {
    Article article = articleService.selectOneArticle("general", id);
    model.addAttribute("article", article);
    return "myclass/general/generalEdit";
  }

  @PostMapping("/commentwrite")
  public String generalComment(Article article, int id, Comment comment, int board_id) {
    System.out.println("댓글달기 타는지안타는지 궁금해 !");
    comment.setUsername(Helper.userName());
    comment.setArticle_id(id);
    commentService.insertComment(comment);
    return "redirect:/myclass/board/read?article_id=" + article.getId() + "&board_id=" + article.getBoard_id();
  }

  @GetMapping("/commentdelete")
  public String generalCommnerDelete(int id, int articleId, int board_id) {
    System.out.println("삭제 탓냐? 안탓냐?");
    Comment comment = commentService.selectOnecomment(id);
    int article_id = comment.getArticle_id();
    commentService.deleteComment(id);
    return "redirect:/myclass/board/read?article_id=" + articleId + "&board_id=" + board_id;
  }
}