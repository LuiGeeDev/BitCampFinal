package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ChecklistDao;
import kr.or.bit.model.Checklist;

@Controller
public class TestController {
  
  @Autowired
  private SqlSession sqlSession;
  
  
  @GetMapping("/checklist")
  public String showChecklist(Model model) {
    ChecklistDao checklistdao = sqlSession.getMapper(ChecklistDao.class);
    
    List<Checklist> checklist =checklistdao.selectAllChecklist();
    System.out.println(checklist);
    model.addAttribute("checklist", checklist);
    return "checklist";
  }
  
  @PostMapping("/checklistinput")
  public String insertChecklist(Model model, @RequestParam("content") String content, @RequestParam("writer_username") String writer_username) {
    System.out.println(content);
    ChecklistDao checklistdao = sqlSession.getMapper(ChecklistDao.class);
    Checklist checklist = new Checklist();
    checklist.setId(1);
    checklist.setContent(content);
    checklist.setGroup_id(1);
    checklist.setWriter_username(writer_username);
    checklistdao.insertChecklist(checklist);
    
    System.out.println(checklist.toString());
    
    
    return "checklist";
  }

}
