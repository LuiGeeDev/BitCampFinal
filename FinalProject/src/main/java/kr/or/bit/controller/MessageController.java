package kr.or.bit.controller;

import java.util.Date;
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
  
  @GetMapping("/message")
  public String messageIndex(Model model) {
    /*String receiver_username = "teacher";*/
    String username = Helper.userName();
    MessageDao messageDao = SqlSession.getMapper(MessageDao.class);
    List<Message> selectall = messageDao.selectAllMessage(username);
    List<Message> selectSenderMessage = messageDao.selectAllSenderMessage(username);
    int countmessage = messageDao.selectCountMessage(username);
    for (Message message: selectall) {
      message.setTimeDate(new Date(message.getTime().getTime()));
    }
    
    for(Message message : selectSenderMessage) {
      message.setTimeDate(new Date(message.getTime().getTime()));
    }
    model.addAttribute("selectall", selectall);
    model.addAttribute("countmessage", countmessage);
    model.addAttribute("sendMessage", selectSenderMessage);
    return "message/message";
  }
}
