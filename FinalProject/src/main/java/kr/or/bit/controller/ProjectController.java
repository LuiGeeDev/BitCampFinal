package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.model.Member;
import kr.or.bit.model.Project;
import kr.or.bit.service.MemberService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/project")
public class ProjectController {
  @Autowired
  private MemberService memberService;
  @Autowired
  private SqlSession sqlSession;
  
  @GetMapping("/createProject")
  public String createProject(Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    List<Member> memberList = memberService.getStudentsList();
    model.addAttribute("user", user);
    model.addAttribute("memberList", memberList);
    return "myclass/teacher/project/createProject";
  }

  @PostMapping("/createProject")
  public String createProject(Project project) {
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    projectDao.insertProject(project);
    return "redirect:/";
  }
}
