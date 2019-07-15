package kr.or.bit.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Course;
import kr.or.bit.model.Files;
import kr.or.bit.model.Member;
import kr.or.bit.service.FileUploadService;
import kr.or.bit.service.MemberService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/mypage")
public class MypageController {
  
  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private MemberService service;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private FileUploadService fileUploadService;


  @GetMapping("/home")
  public String mainPage(Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    String username = Helper.userName();
    List<Article> article1 = articleDao.selectAllArticleByUsername(username);
    List<Article> article2 = articleDao.selectEnableArticleByUsername(username);
    List<Comment> comments = commentDao.selectAllCommentByUsername(username);
    Member user = memberDao.selectMemberByUsername(username);
    System.out.println("hi"+user.getCourse_id());
    Course course = courseDao.selectCourse(user.getCourse_id());
    model.addAttribute("course",course);
    model.addAttribute("comments",comments);
    model.addAttribute("article1",article1);
    model.addAttribute("article2",article2);
    model.addAttribute("user",user);
    return "mypage/mypage";
  }

  @GetMapping("/home/CommentList")
  public String getCommentList(String user) {
    
    return null;
  }
  
  
  @GetMapping("")
  public String updateMember(Model model, Principal principal) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    user.setPassword(user.getPassword());
    model.addAttribute("user", user);
    return "mypage/mypageForm";
  }

  @PostMapping("")
  public String updateMember(Member member, Principal principal, MultipartFile files1, HttpServletRequest request)
      throws IllegalStateException, IOException {
    if (files1 != null) {
      FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
      Files file = fileUploadService.uploadFile(files1, request);
      filesDao.insertFiles(file);
      member.setProfile_photo(file.getFilename());
      service.updateMemberOnlyFile(member);
    } else {
      member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
      service.updateMemberWithoutFile(member);
    }
    return "redirect:/";
  }
  
}
