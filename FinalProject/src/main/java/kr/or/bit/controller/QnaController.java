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
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.General;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.ArticleUpdateService;
import kr.or.bit.service.ArticleVoteService;
import kr.or.bit.service.CommentService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/myclass")
public class QnaController {
  
  private final int QNA_BOARD_ID = 6;
  
  @Autowired
  private SqlSession sqlsession;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ArticleInsertService articleInsertService;
  @Autowired
  private ArticleVoteService articleVoteService;
  @Autowired
  private ArticleUpdateService articleUpdateService;
 
  
  //메인으로 이동
  @GetMapping("/qna/home")
  public String selectAllQna(Model model) {
    List<Article> article = articleService.selectAllArticle("qna",QNA_BOARD_ID);
    model.addAttribute("qnalist",article);
    
    return "myclass/qna/home";
  }
  
  @GetMapping("/qna/content")
  public String selectOneQna(int id, Model model) {
    Article article = articleService.selectOneArticle("qna",id);
    model.addAttribute("qnacontent",article);
    
    return "myclass/qna/content";
  }
  
  @GetMapping("/qna/write")
  public String writeQna(Model model) {
    
    return "myclass/qna/write";
  }
  
  @PostMapping("/qna/write")
  public String writeOkQna(Article article) {
    article.setUsername(Helper.userName());
    article.setBoard_id(QNA_BOARD_ID);
    articleInsertService.writeArticle(article);
    return "redirect:/myclass/qna/home";
  }
  
  @GetMapping("/qna/edit")
  public String editQna(int id, Model model) {
    Article article = articleService.selectOneArticle("qna", id);
    model.addAttribute("article",article);
    return "myclass/qna/edit";
  }
  
  @PostMapping("/qna/edit")
  public String editQnaArticle(Article article) {
    article.setUsername(Helper.userName());
    article.setBoard_id(QNA_BOARD_ID);
    articleUpdateService.updateArticle(article);
    return "redirect:/myclass/qna/content?id="+article.getId();
  }
  
  @GetMapping("/qna/delete")
  public String deleteQna(int id) {
    ArticleDao articleDao = sqlsession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(id);
    
    return "redirect:/myclass/qna/home";
  }
  
  @PostMapping("/qna/commentwrite")
  public String qnaCommentWrite(int id, Comment comment) {
    comment.setUsername(Helper.userName());
    comment.setArticle_id(id);
    commentService.insertComment(comment);   
    return "redirect:/myclass/qna/content?id="+id;
  }
  
  @GetMapping("/qna/commentdelete")
  public String qnaCommentDelete(int id) {
    Comment comment = commentService.selectOnecomment(id);
    int article_id = comment.getArticle_id();
    commentService.deleteComment(id); 
    return "redirect:/myclass/qna/content?id="+article_id;
  } 
  
  @GetMapping("/qna/plusvote")
  public String qnaplustVote(int id) {
    articleVoteService.insertVote(id, Helper.userName());
    return "redirect:/myclass/qna/content?id="+id;
  }
  
}
