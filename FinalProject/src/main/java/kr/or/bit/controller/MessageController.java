package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Message;
import kr.or.bit.utils.Helper;

@Controller
public class MessageController {
  
  
  @Autowired
  private SqlSession SqlSession;
  
  @GetMapping("message")
  public String MessageIndex(Model model, Message message) {
    /*String receiver_username = "teacher";*/
    String username = Helper.userName();
    MessageDao messageDao = SqlSession.getMapper(MessageDao.class);
    List<Message> selectall = messageDao.selectAllMessage(username);
    model.addAttribute("selectall", selectall);
    
    return "message";
  }
  
 /* @GetMapping()
  public String MessageIndexForm() {
    return null;
    
  }*/
  
}
