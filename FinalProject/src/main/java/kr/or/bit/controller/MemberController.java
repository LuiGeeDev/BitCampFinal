package kr.or.bit.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.or.bit.model.Member;
import kr.or.bit.service.MemberService;

@Controller
public class MemberController {
  @Autowired
  private MemberService service;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/mypage")
  public String updateMember(Model model) {
    Member user = service.getMember();
    model.addAttribute("user", user);
    return "mypageForm";
  }

  @PostMapping("/mypage")
  public String updateMember(Member member, Principal principal) {
    // password 암호화
    member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
    service.updateMember(member);
    return "redirect:/";
  }
}
