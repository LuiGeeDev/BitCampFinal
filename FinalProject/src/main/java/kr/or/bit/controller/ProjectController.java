package kr.or.bit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.or.bit.model.Member;
import kr.or.bit.model.Project;
import kr.or.bit.service.MemberService;

@Controller
public class ProjectController {
  @Autowired
  private MemberService memberService;

  @GetMapping("/createProject")
  public String createProject(Model model) {
    Member user = memberService.getMember();
    List<Member> memberList = memberService.getAllMembers();
    model.addAttribute("user", user);
    model.addAttribute("memberList", memberList);
    return "project/createProject";
  }

  @PostMapping("/createProject")
  public String createProject(Project project) {
    return "redirect:/";
  }
}
