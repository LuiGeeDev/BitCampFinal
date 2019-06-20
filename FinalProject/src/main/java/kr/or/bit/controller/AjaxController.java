package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.service.NewsService;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
  @PostMapping("/news")
  public @ResponseBody String getNews() {
    NewsService service = new NewsService();
    String news = service.getNews();
    
    return news;
  }
}
