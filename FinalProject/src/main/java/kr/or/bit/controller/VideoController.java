package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Video;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/video")
public class VideoController {
  private final int VIDEO_BOARD_ID = 2;
  
  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private ArticleInsertService articleInsertService;  
  @Autowired
  private ArticleService articleService;
  
  @GetMapping("/home")
  public String videoHome(Model model) {
    List<Article> videoList = articleService.selectAllArticle("video", VIDEO_BOARD_ID);
    model.addAttribute("videoList", videoList);
    
    return "video/home";
  }

  @GetMapping("/detail")
  public String getDetail(int id, Model model) {
    /*
     * parameter로 받은 아이디 값을 이용, 해당하는 글을 불러와서 페이지에 글을 넘겨준다
     */
    System.out.println(id);
    Article article = articleService.selectOneArticle("video", id);
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
    
    model.addAttribute("article", article);
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
    article.setBoard_id(VIDEO_BOARD_ID);
    
    Video video = new Video();
    int beginIndex = "https://youtu.be/".length();
    video.setVideo_id(url.substring(beginIndex));

    articleInsertService.writeArticle(article, video);
    return "redirect:/video/detail?id=" + article.getId();
  }
}
