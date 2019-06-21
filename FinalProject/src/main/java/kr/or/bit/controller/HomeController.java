package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.bit.service.NewsService;

@Controller
public class HomeController {
  @GetMapping("/")
  public String home(Model model) {
    return "main";
  }

  @GetMapping("/menu")
  public String menu() {
    return "menu";
  }
}
