package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myclass/troubleshooting")
public class TroubleShootingController {
  @GetMapping("")
  public String troubleshootingPage() {
    return "myclass/troubleshooting/main";
  }

  @GetMapping("/write")
  public String writeNewIssue() {
    return "myclass/troubleshooting/write";
  }

  @GetMapping("/read")
  public String readIssue(int id) {
    return "myclass/troubleshooting/detail";
  }
}
