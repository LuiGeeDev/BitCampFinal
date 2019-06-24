package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.bit.dao.ChecklistDao;
import kr.or.bit.model.Checklist;

@Controller
public class TestController {
  
  @Autowired
  private SqlSession sqlSession;
  
  
  @GetMapping("/checklist")
  public String Checklist(Model model) {
    ChecklistDao checklistdao = sqlSession.getMapper(ChecklistDao.class);
    
    List<Checklist> checklist =checklistdao.selectAllChecklist();
    System.out.println(checklist);
    model.addAttribute("checklist", checklist);
    return "checklist";
  }
}
