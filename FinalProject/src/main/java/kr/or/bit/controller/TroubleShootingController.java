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
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Group;
import kr.or.bit.model.Member;
import kr.or.bit.model.Project;
import kr.or.bit.service.TroubleShootingService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/myclass/project/troubleshooting")
public class TroubleShootingController {
  @Autowired
  private SqlSession sqlSession;

  @Autowired
  private TroubleShootingService service;

  @GetMapping("")
  public String troubleshootingPage(@RequestParam("id") int board_id,
      @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String criteria,
      @RequestParam(required = false) String word, int project_id, String q, Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);

    List<Article> articlesOpened = service.getIssueOpened(board_id, page, criteria, word);
    List<Article> articlesClosed = service.getIssueClosed(board_id, page, criteria, word);

    Member user = memberDao.selectMemberByUsername(Helper.userName());

    Group group = groupDao.selectGroupByProjectId(project_id, user.getUsername());
    Project project = projectDao.selectProject(project_id);
    Board ts = boardDao.selectTroubleShootingBoard(user.getCourse_id(), project.getSeason(), group.getGroup_no());

    model.addAttribute("criteria", criteria);
    model.addAttribute("word", word);
    model.addAttribute("group", group);
    model.addAttribute("ts", ts);
    model.addAttribute("project", project);
    model.addAttribute("articlesOpened", service.numberOfIssueOpened(board_id, criteria, word));
    model.addAttribute("articlesClosed", service.numberOfIssueClosed(board_id, criteria, word));
    model.addAttribute("closed", (q == null) ? false : true);
    model.addAttribute("articleList", (q == null) ? articlesOpened : articlesClosed);
    model.addAttribute("pager", (q == null) ? service.getPager(board_id, page, criteria, word, false) : service.getPager(board_id, page, criteria, word, true));
    return "myclass/troubleshooting/main";
  }

  @GetMapping("/write")
  public String writePage(int board_id, int project_id, Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    StackDao stackDao = sqlSession.getMapper(StackDao.class);

    Member user = memberDao.selectMemberByUsername(Helper.userName());

    Group group = groupDao.selectGroupByProjectId(project_id, user.getUsername());
    Project project = projectDao.selectProject(project_id);
    Board ts = boardDao.selectTroubleShootingBoard(user.getCourse_id(), project.getSeason(), group.getGroup_no());
    
    model.addAttribute("tags", stackDao.showTagList());
    model.addAttribute("group", group);
    model.addAttribute("ts", ts);
    model.addAttribute("project", project);
    return "myclass/troubleshooting/write";
  }

  @PostMapping("/write")
  public String writeNewIssue(Article article, int group_id, int project_id, String tag) {
    return "redirect:/myclass/project/troubleshooting/read?id=" + service.writeIssue(article, group_id, tag) + "&project_id=" + project_id;
  }

  @GetMapping("/read")
  public String readIssue(int id, int project_id, Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);

    Member user = memberDao.selectMemberByUsername(Helper.userName());

    Group group = groupDao.selectGroupByProjectId(project_id, user.getUsername());
    Project project = projectDao.selectProject(project_id);
    Board ts = boardDao.selectTroubleShootingBoard(user.getCourse_id(), project.getSeason(), group.getGroup_no());

    Article article = service.getIssue(id);

    model.addAttribute("group", group);
    model.addAttribute("ts", ts);
    model.addAttribute("project", project);
    model.addAttribute("article", article);
    return "myclass/troubleshooting/detail";
  }

  @GetMapping("/change")
  public String changeIssueStatus(int id, int project_id) {
    service.changeIssueStatus(id);
    return "redirect:/myclass/project/troubleshooting/read?id=" + id + "&project_id=" + project_id;
  }
  
  @PostMapping("/comment")
  public String writeComment(Comment comment, int project_id) {
    service.writeComment(comment);
    return "redirect:/myclass/project/troubleshooting/read?id=" + comment.getArticle_id() + "&project_id=" + project_id;
  }
  
  @GetMapping("/delete")
  public String deleteArticle(int board_id, int project_id, Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    article = articleDao.selectOneArticle(article.getId());
    service.deleteIssue(article);
    return "redirect:/myclass/project/troubleshooting?id=" + board_id + "&project_id=" + project_id;
  }
}
