package kr.or.bit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dao.ChecklistDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.TimelineDao;
import kr.or.bit.model.Checklist;
import kr.or.bit.model.Member;
import kr.or.bit.model.Timeline;
import kr.or.bit.utils.Helper;

@RestController
@RequestMapping(path = "/axios")
public class AxiosContoller {
  @Autowired
  private SqlSession sqlSession;

  @GetMapping("/addTodo")
  @Transactional
  public Map<String, Object> addTodo(@RequestParam Map<String, Object> todo) {
    ChecklistDao checklistDao = sqlSession.getMapper(ChecklistDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    Member member = memberDao.selectMemberByUsername(Helper.userName());
    Checklist checklist = new Checklist();
    checklist.setChecked(0);
    checklist.setWriter_username(member.getUsername());
    checklist.setContent(todo.get("todo") + "");
    checklist.setGroup_id(Integer.parseInt(todo.get("group_id") + ""));
    checklistDao.insertChecklist(checklist);
    
    Map<String, Object> returnChecklist = new HashMap<String, Object>();
    returnChecklist.put("checklist", checklist);
    return returnChecklist;
  }

  @GetMapping("/doneToggle")
  public Map<String, Object> doneToggle(@RequestParam Map<String, Object> todolist) {
    ChecklistDao checklistDao = sqlSession.getMapper(ChecklistDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    TimelineDao timelineDao = sqlSession.getMapper(TimelineDao.class);
    
    List<Checklist> checklist = checklistDao.selectAllChecklist(Integer.parseInt(todolist.get("group_id") + ""));
    Member member = memberDao.selectMemberByUsername(Helper.userName());
    Checklist todo = checklist.get(Integer.parseInt(todolist.get("index") + ""));
    if (todo.getChecked() == 1) {
      checklist.get(Integer.parseInt(todolist.get("index") + "")).setChecked(0);
    } else if (todo.getChecked() == 0) {
      checklist.get(Integer.parseInt(todolist.get("index") + "")).setChecked(1);
    }
    
    checklist.get(Integer.parseInt(todolist.get("index") + "")).setChecker_username(member.getUsername());
    checklistDao.updateChecklist(checklist.get(Integer.parseInt(todolist.get("index") + "")));
    
    if(!(todo.getChecked() == 0 )) {
      Timeline timeline = new Timeline();
      timeline.setTitle("체크리스트 완료");
      timeline.setContent(todo.getContent());
      timeline.setGroup_id(Integer.parseInt(todolist.get("group_id")+""));
      timeline.setUsername(Helper.userName());
      timelineDao.insertTimeline(timeline);
    }
    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("checklist", checklist);
    returnMap.put("index", todolist.get("index"));
    return returnMap;
  }

  @GetMapping("/deleteTodo")
  public Map<String, Object> select(@RequestParam Map<String, Object> todo) {
    ChecklistDao checklistDao = sqlSession.getMapper(ChecklistDao.class);
    List<Checklist> checklist = checklistDao.selectAllChecklist(Integer.parseInt(todo.get("group_id") + ""));
    checklistDao.deleteChecklist(checklist.remove(Integer.parseInt(todo.get("index") + "")).getId());
    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("index", Integer.parseInt(todo.get("index") + ""));
    return returnMap;
  }
}
