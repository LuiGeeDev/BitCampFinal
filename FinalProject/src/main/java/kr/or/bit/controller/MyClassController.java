package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.General;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.BoardService;

@Controller
@RequestMapping("/myclass")
public class MyClassController {
  @Autowired
  private BoardService boardService;

  @Autowired
  private ArticleService articleService;

  @Autowired
  private ArticleInsertService articleInsertService;

  @GetMapping("/board")
  public String boardPage(int boardId, @RequestParam(name = "page", defaultValue = "1") int page, Model model) {
    List<Article> articleList = boardService.getArticlesByPage(boardId, page);
    model.addAttribute("articles", articleList);
    return "myclass/board/list";
  }

  @GetMapping("/board/read")
  public String readArticle(int article_id, Model model) {
    Article article = articleService.selectOneArticle("general", article_id);
    model.addAttribute("article", article);
    return "myclass/board/read";
  }

  @GetMapping("/board/write")
  public String writePage() {
    return "myclass/board/write";
  }

  @PostMapping("/board/write")
  public String writeArticle(Article article, General general) {
    articleInsertService.writeArticle(article, general);
    return "redirect:/myclass/board/list";
  }

  @GetMapping("/project")
  public String projectPage() {
    return "myclass/project/main";
  }

  @GetMapping("/troubleshooting")
  public String troubleshootingPage() {
    return "myclass/troubleshooting/main";
  }
  
  @GetMapping("/troubleshooting/write")
  public String writeNewIssue() {
    return "myclass/troubleshooting/write";
  }
  
  @GetMapping("/troubleshooting/read") 
  public String readIssue() {
    return "myclass/troubleshooting/detail";
  }

  @GetMapping("/chat")
  public String chatPage() {
    return "myclass/chat/main";
  }

  @GetMapping("/qna")
  public String qnaPage() {
    return "myclass/qna/home";
  }

  @GetMapping("/create")
  public String createProject() {
    return "myclass/create/main";
  }

  @GetMapping("/homework")
  public String homework() {
    return "myclass/homework/list";
  }
  
  @GetMapping("/homework/detail")
  public String homeworkDetail() {
    return "myclass/homework/detail";
  }
  
  @GetMapping("/homework/create")
  public String createHomework() {
    return "myclass/homework/create";
  }
  
  @GetMapping("/main/home")
  public String mainPage() {
    return "myclass/main/home";
  }
}
