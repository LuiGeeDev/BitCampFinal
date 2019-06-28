package kr.or.bit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.model.Article;
import kr.or.bit.service.ArticleService;

@Controller
@RequestMapping("/stack")
public class StackController {
  
  @Autowired
  private SqlSession sqlsession;
  
  @Autowired
  ArticleService articleService;
  
  //stack 메인으로 이동
  @GetMapping("/home")
  public String selectAllStack(Model model) {
    List<Article> article = articleService.selectAllArticle(2);
    model.addAttribute("stacklist",article);
    return "stack/home";
  }
  
  //stack 게시물 상세보기 버튼
  @GetMapping("/content")
  public String selectStack(int id, Model model) {
    Article article = articleService.selectOneArticle(id);
    model.addAttribute("stackcontent",article);
    return "stack/content";
  }
  
  @GetMapping("/write")
  public String stackWrite(Model model) {
    
    return "stack/write";
  }
  
}
