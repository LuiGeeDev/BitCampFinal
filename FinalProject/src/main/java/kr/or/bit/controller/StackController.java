package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stack")
public class StackController {
  
  @Autowired
  private SqlSession sqlsession;
  
  //stack 메인으로 이동
  @GetMapping("/home")
  public String selectAllStack(Model model) {
    
    return "stack/home";
  }
  
  //stack 게시물 상세보기 버튼
  @GetMapping("/content")
  public String selectStack(Model model) {
    
    return "stack/content";
  }
  
}
