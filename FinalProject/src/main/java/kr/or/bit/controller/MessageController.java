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

/*
 * 쪽지 페이지에 관련된  메서드를 포함한 컨트롤러
 * */

@Controller
public class MessageController {
  @Autowired
  private SqlSession SqlSession;

  @GetMapping("/message")
  public String messageIndex(Model model) {
    String username = Helper.userName();
    MessageDao messageDao = SqlSession.getMapper(MessageDao.class);
    List<Message> selectall = messageDao.selectAllMessage(username);
    List<Message> selectSenderMessage = messageDao.selectAllSenderMessage(username);
    int countmessage = messageDao.selectCountMessage(username);
    for (Message message : selectall) {
      message.setTimeDate(new Date(message.getTime().getTime()));
      message.setTimeLocal(message.getTime().toLocalDateTime());
    }

    for (Message message : selectSenderMessage) {
      message.setTimeDate(new Date(message.getTime().getTime()));
      message.setTimeLocal(message.getTime().toLocalDateTime());
    }

    model.addAttribute("selectall", selectall);
    model.addAttribute("countmessage", countmessage);
    model.addAttribute("sendMessage", selectSenderMessage);
    return "message/message";
  }
}
