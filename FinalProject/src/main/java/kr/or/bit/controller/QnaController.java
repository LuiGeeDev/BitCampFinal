package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myclass")
public class QnaController {
  
 
  
  @GetMapping("/qna/home")
  public String selectAllQna(Model model) {
    
    return "myclass/qna/home";
  }
  
  @GetMapping("/qna/content")
  public String selectQna(Model model) {
    
    return "myclass/qna/content";
  }
  @GetMapping("/qna/write")
  public String qnaWrite(Model model) {
    
    return "myclass/qna/write";
  }
  
}
