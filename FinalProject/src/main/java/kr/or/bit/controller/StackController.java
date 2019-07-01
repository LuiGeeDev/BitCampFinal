package kr.or.bit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.CommentService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/stack")
public class StackController {
  private final int VIDEO_BOARD_ID = 1;
  
  @Autowired
  private SqlSession sqlsession;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;
  
  //stack 메인으로 이동
  @GetMapping("/home")
  public String selectAllStack(Model model) {
    List<Article> article = articleService.selectAllArticle("qna",2);
    model.addAttribute("stacklist",article);
    return "stack/home";
  }
  
  //stack 게시물 상세보기 버튼
  @GetMapping("/content")
  public String selectStack(int id, Model model) {
    Article article = articleService.selectOneArticle("qna",id);
    model.addAttribute("stackcontent",article);
    return "stack/content";
  }
  
  //글쓰기 폼 화면으로..
  @GetMapping("/write")
  public String stackWrite(Model model) {   
    return "stack/write";
  }
  
  //글쓰기 버튼 누르기..
  @PostMapping("/write")
  public String stackWriteOk(Article article) {    
    return "stack/home";
  }
  
  @PostMapping("/commentwrite")
  public String stackCommentWrite(int id, Comment comment) {
    comment.setUsername(Helper.userName());
    comment.setArticle_id(id);
    commentService.insertComment(comment);   
    return "redirect:/stack/content?id="+id;
  }
  
  
  @GetMapping("/commentdelete")
  public String stackCommentDelete(int id) {
    Comment comment = commentService.selectOnecomment(id);
    int article_id = comment.getArticle_id();
    commentService.deleteComment(id); 
    return "redirect:/stack/content?id="+article_id;
  } 
}
