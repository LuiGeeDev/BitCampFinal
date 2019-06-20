package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.service.NewsService;

@Controller
public class HomeController {
  @Autowired
  private SqlSession sqlsession;

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("message", "world");
    return "main";
  }
  
  @GetMapping("/menu")
  public String menu(Model model) {
    model.addAttribute("message", "world");
    return "menu";
  }
  

}
