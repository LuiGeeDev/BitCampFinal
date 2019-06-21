package kr.or.bit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
  
  @GetMapping("/login")
  public String login() {
    return "login";
  }
  
  @GetMapping("/login-error")
  public String loginFailed(Model model) {
    model.addAttribute("error", true);
    return "login";
  }
  
  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    
    return "redirect:login";
  }
}
