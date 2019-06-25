package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Member;
import kr.or.bit.model.Message;

@Controller
public class HomeController {
  @Autowired
  private SqlSession sqlSession;
  
  @GetMapping("/")
  public String home(Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    
    Member user = memberDao.selectMemberByUsername(username);
    
    List<Message> mainMessage = messageDao.selectMainMessage(username);
    
	  model.addAttribute("user", user);
	  model.addAttribute("mainMessage", mainMessage);
    return "main";
  }

  @GetMapping("/menu")
  public String menu() {
    return "menu";
  }
}
