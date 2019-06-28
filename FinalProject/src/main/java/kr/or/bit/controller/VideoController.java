package kr.or.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Video;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/video")
public class VideoController {
  @Autowired
  private ArticleInsertService articleInsertService;
  
  @GetMapping("/home")
  public String videoHome() {
    return "video/home";
  }

  @GetMapping("/detail")
  public String getDetail(Model model) {
    /*
     * parameter로 받은 아이디 값을 이용, 해당하는 글을 불러와서 페이지에 글을 넘겨준다
     */
    return "video/detail";
  }

  @GetMapping("/write")
  public String getWritePage() {
    return "video/write";
  }

  @PostMapping("/write")
  public String writeVideoArticle(Article article, String url) {
    /*
     * 글 쓰기 데이터를 받아서 해당 글의 페이지로 넘겨준다.
     */
    article.setUsername(Helper.userName());
    article.setBoard_id(2);
    
    Video video = new Video();
    int beginIndex = "https://youtu.be/".length();
    video.setVideo_id(url.substring(beginIndex));

    articleInsertService.writeArticle(article, video);
    return "redirect:/video/detail";
  }
}
