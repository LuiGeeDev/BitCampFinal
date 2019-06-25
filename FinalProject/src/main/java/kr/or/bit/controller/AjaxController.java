package kr.or.bit.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.service.NewsService;

@RestController
@RequestMapping("/ajax")
public class AjaxController {  
  @PostMapping("/news")
  public String getNews(Model model) {
    NewsService service = new NewsService();
    String news = service.getNews();
    
    return news;
  }
}
