package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
  
  
  @GetMapping("message")
  public String MessageIndex() {
    return "message";
  }
  
 /* @GetMapping()
  public String MessageIndexForm() {
    return null;
    
  }*/
  
}
