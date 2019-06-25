package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Message;

@Controller
public class MessageController {
  
  
  @Autowired
  private SqlSession SqlSession;
  
  @GetMapping("message")
  public String MessageIndex(Model model, Message message) {
    /*String receiver_username = "teacher";*/
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    MessageDao messageDao = SqlSession.getMapper(MessageDao.class);
    List<Message> selectall = messageDao.selectAllMessage(username);
    System.out.println("selectall : " + selectall);
    model.addAttribute("selectall", selectall);
    
    return "message";
  }
  
 /* @GetMapping()
  public String MessageIndexForm() {
    return null;
    
  }*/
  
}
