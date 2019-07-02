package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralBoardController {
  
  @Autowired
  private SqlSession sqlsession;
  
  @GetMapping("/generalBoard")
  public String GeneralBoard() {
    
    return "generalBoard";
  }
  
}
