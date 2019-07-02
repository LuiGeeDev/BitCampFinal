package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myclass")
public class MyClassController {
  @GetMapping("/project")
  public String projectPage() {
    return "myclass/project/main";
  }
  
  @GetMapping("/troubleshooting")
  public String troubleshootingPage() {
    return "myclass/troubleshooting/main";
  }
  
  @GetMapping("/chat")
  public String chatPage() {
    return "myclass/chat/main";
  }
  
  @GetMapping("/qna")
  public String qnaPage() {
    return "myclass/qna/home";
  }
  
  @GetMapping("/create")
  public String createProject() {
    return "myclass/create/main";
  }
}
