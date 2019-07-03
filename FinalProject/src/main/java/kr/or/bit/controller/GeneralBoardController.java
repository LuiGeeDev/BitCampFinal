package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/general")
public class GeneralBoardController {
  
  @Autowired
  private SqlSession sqlsession;
  
  @GetMapping("/generalBoard")
  public String generalBoard() {
    
    return "myclass/general/generalBoard";
  }
  
  @GetMapping("/detail")
  public String generalBoardDetail() {
    return "myclass/general/generalBoardDetail";
  }
  
  @GetMapping("/generalBoardWrite")
  public String generalBoardWrite() {
    return "myclass/general/generalBoardWrite";
  }
  
}
