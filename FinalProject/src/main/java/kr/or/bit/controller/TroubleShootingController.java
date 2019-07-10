package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Group;
import kr.or.bit.service.TroubleShootingService;

@Controller
@RequestMapping("/myclass/project/troubleshooting")
public class TroubleShootingController {
  @Autowired
  private SqlSession sqlSession;

  @Autowired
  private TroubleShootingService service;

  @GetMapping("")
  public String troubleshootingPage(int group_id,
      @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String criteria,
      @RequestParam(required = false) String word, String q, Model model) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);

    Group group = groupDao.selectGroupById(group_id);

    List<Article> articlesOpened = service.getIssueOpened(group_id, page, criteria, word);
    List<Article> articlesClosed = service.getIssueClosed(group_id, page, criteria, word);

    model.addAttribute("criteria", criteria);
    model.addAttribute("word", word);
    model.addAttribute("group", group);
    model.addAttribute("articlesOpened", service.numberOfIssueOpened(group_id, criteria, word));
    model.addAttribute("articlesClosed", service.numberOfIssueClosed(group_id, criteria, word));
    model.addAttribute("closed", (q == null) ? false : true);
    model.addAttribute("articleList", (q == null) ? articlesOpened : articlesClosed);
    model.addAttribute("pager", (q == null) ? service.getPager(group_id, page, criteria, word, false) : service.getPager(group_id, page, criteria, word, true));
    return "myclass/troubleshooting/main";
  }

  @GetMapping("/write")
  public String writePage(int group_id, Model model) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    StackDao stackDao = sqlSession.getMapper(StackDao.class);

    Group group = groupDao.selectGroupById(group_id);
    
    model.addAttribute("tags", stackDao.showTagList());
    model.addAttribute("group", group);
    return "myclass/troubleshooting/write";
  }

  @PostMapping("/write")
  public String writeNewIssue(Article article, int group_id, String tag) {
    return "redirect:/myclass/project/troubleshooting/read?id=" + service.writeIssue(article, group_id, tag) + "&group_id=" + group_id;
  }

  @GetMapping("/read")
  public String readIssue(int id, int group_id, Model model) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    Group group = groupDao.selectGroupById(group_id);

    Article article = service.getIssue(id);

    model.addAttribute("group", group);
    model.addAttribute("article", article);
    return "myclass/troubleshooting/detail";
  }

  @GetMapping("/change")
  public String changeIssueStatus(int id, int group_id) {
    service.changeIssueStatus(id);
    return "redirect:/myclass/project/troubleshooting/read?id=" + id + "&group_id=" + group_id;
  }
  
  @PostMapping("/comment")
  public String writeComment(Comment comment, int group_id) {
    service.writeComment(comment);
    return "redirect:/myclass/project/troubleshooting/read?id=" + comment.getArticle_id() + "&group_id=" + group_id;
  }
  
  @GetMapping("/delete")
  public String deleteArticle(int group_id, Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    article = articleDao.selectOneArticle(article.getId());
    service.deleteIssue(article);
    return "redirect:/myclass/project/troubleshooting?group_id=" + group_id;
  }
  
  @GetMapping("/delete/comment")
  public String deleteComment(int group_id, Comment comment) {
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    comment = commentDao.selectOneComment(comment.getId());
    service.deleteComment(comment);
    return "redirect:/myclass/project/troubleshooting/read?id=" + comment.getArticle_id() + "&group_id=" + group_id;
  }
}
