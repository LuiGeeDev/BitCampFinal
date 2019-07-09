package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.model.Board;
import kr.or.bit.model.Group;
import kr.or.bit.model.Member;
import kr.or.bit.model.Project;
import kr.or.bit.service.MemberService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("myclass/project")
public class ProjectController {
  @Autowired
  private MemberService memberService;
  @Autowired
  private SqlSession sqlSession;

  @GetMapping("")
  public String projectPage(@RequestParam("id") int project_id, Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    
    Member user = memberDao.selectMemberByUsername(Helper.userName());

    Group group = groupDao.selectGroupByProjectId(project_id, user.getUsername());
    Board ts = boardDao.selectTroubleShootingBoard(user.getCourse_id(), group.getGroup_no());
    Project project = projectDao.selectProject(project_id);

    model.addAttribute("group", group);
    model.addAttribute("ts", ts);
    model.addAttribute("project", project);
    // 스케줄, 타임라인, 체크리스트, 트러블슈팅 글
    return "myclass/project/main";
  }

  @GetMapping("/chat")
  public String chatPage(int project_id, Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    
    Member user = memberDao.selectMemberByUsername(Helper.userName());

    Group group = groupDao.selectGroupByProjectId(project_id, user.getUsername());
    Board ts = boardDao.selectTroubleShootingBoard(user.getCourse_id(), group.getGroup_no());
    Project project = projectDao.selectProject(project_id);

    model.addAttribute("group", group);
    model.addAttribute("ts", ts);
    model.addAttribute("project", project);
    
    return "myclass/chat/main";
  }
}
