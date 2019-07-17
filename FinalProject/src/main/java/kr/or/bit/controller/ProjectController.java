package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dao.ChecklistDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.TimelineDao;
import kr.or.bit.model.Checklist;
import kr.or.bit.model.Group;
import kr.or.bit.model.Timeline;
import kr.or.bit.utils.Helper;
@Controller
@RequestMapping("myclass/project")
public class ProjectController {
  @Autowired
  private SqlSession sqlSession;

  @GetMapping("")
  public String projectPage(int group_id, Model model) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    ChecklistDao checklistDao = sqlSession.getMapper(ChecklistDao.class);
    TimelineDao timelineDao = sqlSession.getMapper(TimelineDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Timeline> timelineList = timelineDao.selectTimelineByGroupId(group_id);
    
    Group group = groupDao.selectGroupById(group_id);
    
    List<Checklist> checklist = checklistDao.selectAllChecklist(group_id);
    List<String> checklistContents = new ArrayList<String>();
    for(Checklist todo : checklist) {
      checklistContents.add(todo.getContent());
    }
    
    for(Timeline timeline : timelineList) {
      timeline.setWriter(memberDao.selectMemberByUsername(timeline.getUsername()));
    }
    
    model.addAttribute("group", group);
    model.addAttribute("checklist",checklist);
    model.addAttribute("timelineList",timelineList);
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
