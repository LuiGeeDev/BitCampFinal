package kr.or.bit.controller;

import javax.mail.MessagingException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;
import kr.or.bit.service.MailService;

/*
 * 로그인 페이지에 관련된 메서드를 포함한 컨트롤러
 * */
@Controller
public class LoginController {
  @Autowired
  private SqlSession sqlSession;

  @Autowired
  private MailService mailService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/login-error")
  public String loginFailed(Model model) {
    model.addAttribute("error", true);
    return "login";
  }

  @PostMapping("/forgot-password")
  public @ResponseBody boolean sendNewPassword(@RequestParam("username") String username,
      @RequestParam("email") String email) throws MessagingException {

    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    boolean result = false;
    int tempPassword = (int) (Math.random() * 9000) + 1000;

    Member member = memberDao.selectMemberByUsername(username);
    if (member == null || member.getEmail() == null) {
      return result;
    }

    if (member.getEmail().equals(email)) {
      member.setPassword(bCryptPasswordEncoder.encode(String.valueOf(tempPassword)));
      memberDao.updateMember(member);
      mailService.sendNewPasswordEmail(tempPassword, member);
      result = true;
    }

    return result;
  }
}
