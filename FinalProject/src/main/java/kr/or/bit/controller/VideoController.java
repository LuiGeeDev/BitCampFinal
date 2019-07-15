package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.dao.ViewCountDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Video;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.ArticleUpdateService;
import kr.or.bit.service.ArticleVoteService;
import kr.or.bit.service.BoardService;
import kr.or.bit.service.CommentService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/video")
public class VideoController {
  private final int VIDEO_BOARD_ID = 5;
  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private ArticleInsertService articleInsertService;
  @Autowired
  private ArticleUpdateService articleUpdateService;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private ArticleVoteService articleVoteService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private BoardService boardService;

  @GetMapping("")
  public String videoHome(Model model) {
    List<Article> videoList = articleService.selectAllArticle("video", VIDEO_BOARD_ID, null);
    model.addAttribute("videoList", videoList);
    return "video/home";
  }

  @GetMapping("/detail")
  public String getDetail(int id, Model model) {
    /*
     * parameter로 받은 아이디 값을 이용, 해당하는 글을 불러와서 페이지에 글을 넘겨준다
     */
    Article article = articleService.selectOneArticle("video", id);
    articleUpdateService.viewCount(article);
    model.addAttribute("voteStatus", articleVoteService.selectVote(id, Helper.userName()));
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
    return "redirect:/video";
  }

  @GetMapping("/edit")
  public String getEditPage(int id, String video_id, Model model) {
    Article article = articleService.selectOneArticle("video", id);
    model.addAttribute("article", article);
    model.addAttribute("video_id", "https://youtu.be/" + video_id);
    return "video/edit";
  }

  @PostMapping("/edit")
  public String editVideoArticle(Article article, String url) {
    article.setUsername(Helper.userName());
    article.setBoard_id(VIDEO_BOARD_ID);
    Video video = new Video();
    int beginIndex = "https://youtu.be/".length();
    video.setVideo_id(url.substring(beginIndex));
    articleUpdateService.updateArticle(article);
    articleUpdateService.updateArticleOption(article.getId(), video);
    return "redirect:/video";
  }

  @GetMapping("/delete")
  public String deleteVideo(int id) {
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
    videoDao.deleteVideo(id);
    return "redirect:/video";
  }

 /* @PostMapping("/commentwrite")
  public String writeCommentVideo(int id, Comment comment) {
    comment.setUsername(Helper.userName());
    comment.setArticle_id(id);
    commentService.insertComment(comment);
    return "redirect:/video/detail?id=" + id;
  }*/

  @PostMapping("/commentwrite")
  public @ResponseBody List<Comment> commentWrite(int article_id, Comment comment) {
    boardService.writeComment(article_id, comment);
    List<Comment> commentList = boardService.getCommentList(article_id);
    return commentList;
  }
  
  
  
  @GetMapping("/commentdelete")
  public String deleteCommentVideo(int id) {
    Comment comment = commentService.selectOnecomment(id);
    int article_id = comment.getArticle_id();
    commentService.deleteComment(id);
    return "redirect:/video/detail?id=" + article_id;
  }
  
  @GetMapping("/realDelete")
  public String readDeleteVideo(int id) {
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
    videoDao.readDeleteVideo(id);
    return "redirect:/video";
  }
  
  @GetMapping("/activation")
  public String videoActivation(int id) {
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
    videoDao.deleteVideo(id);
    return "redirect:/video";
  }
}
