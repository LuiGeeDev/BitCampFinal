package kr.or.bit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.service.BoardService;

@Controller
@RequestMapping("/myclass/board")
public class BoardController {
  @Autowired
  private BoardService boardService;

  @GetMapping("")
  public String listPage(int board_id, @RequestParam(defaultValue = "1") int page,
      @RequestParam(required = false) String sort, @RequestParam(required = false) String search, @RequestParam(required = false) String criteria, Model model) {
    List<Article> articles = null;
    if (sort == null && search == null) {
      articles = boardService.getArticlesByPage(board_id, page);
    } else if (sort != null) {
      articles = boardService.getArticlesSorted(board_id, page, sort);
    } else if (search != null) {
      articles = boardService.getArticlesBySearchWord(board_id, page, search, criteria);
    }

    model.addAttribute("board", boardService.getBoardInfo(board_id));
    model.addAttribute("articles", articles);

    return "myclass/general/generalBoard";
  }

  @GetMapping("/write")
  public String writePage(int board_id, Model model) {
    model.addAttribute("board", boardService.getBoardInfo(board_id));
    return "myclass/general/generalBoardWrite";
  }

  @PostMapping("/write")
  public String writeArticle(Article article, MultipartFile file1, MultipartFile file2, HttpServletRequest request) {
    return "redirect:/myclass/board/read?article_id=" + boardService.writeArticle(article, file1, file2, request) + "&board_id=" + article.getBoard_id();
  }

  @GetMapping("/read")
  public String readArticle(int article_id, int board_id, Model model) {
    Article article = boardService.readArticle(article_id);
    System.out.println(article);
    System.out.println();
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
  public String updateArticle(Article article, List<MultipartFile> files, int board_id) {
    System.out.println(files);
    boardService.updateArticle(article, files);
    System.out.println(article);
    return "redirect:/myclass/board/read?article_id=" + article.getId() + "&board_id=" + article.getBoard_id(); 
  }

  @GetMapping("/delete")
  public String deleteArticle(int article_id) {
    Article article = boardService.getArticleForUpdateOrDelete(article_id);
    boardService.deleteArticle(article);

    return "redirect:/myclass/board?board_id=" + article.getBoard_id();
  }
}