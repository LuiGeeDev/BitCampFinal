package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {
  
 
  
  @GetMapping("/home")
  public String selectAllQna(Model model) {
    
    return "qna/home";
  }
  
}
