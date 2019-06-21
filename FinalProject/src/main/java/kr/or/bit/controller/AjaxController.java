package kr.or.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import kr.or.bit.service.NewsService;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
  @Autowired
  private View jsonView;
  
  @PostMapping("/news")
  public View getNews(Model model) {
    NewsService service = new NewsService();
    String news = service.getNews();
    model.addAttribute("news", news);
    
    return jsonView;
  }
}
