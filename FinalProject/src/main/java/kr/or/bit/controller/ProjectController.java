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
  private SqlSession sqlSession;

  @GetMapping("")
  public String projectPage(int group_id, Model model) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    Group group = groupDao.selectGroupById(group_id);

    model.addAttribute("group", group);
    return "myclass/project/main";
  }

  @GetMapping("/chat")
  public String chatPage(int group_id, Model model) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    Group group = groupDao.selectGroupById(group_id);

    model.addAttribute("group", group);
    return "myclass/chat/main";
  }
}
