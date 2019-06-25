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
  
  @GetMapping("/home")
  public String test(Model model) {
    
    return "stack/home";
  }
  
}
