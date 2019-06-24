package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Message;

@Controller
public class MessageController {
  
  @Autowired
  private SqlSession sqlsession;
  
  @RequestMapping("/message")
  public String selectTest(Model model) {
    String teacher = "teacher";
    String student = "student";
    
    MessageDao messagedao = sqlsession.getMapper(MessageDao.class);
    MessageDao messagedao2 = sqlsession.getMapper(MessageDao.class);
    MessageDao messagedao3 = sqlsession.getMapper(MessageDao.class);
    MessageDao messagedao4 = sqlsession.getMapper(MessageDao.class);
    
    List<Message> list = messagedao.selectAllMessage(teacher);
    List<Message> sender = messagedao2.selectMessageById(student);
    List<Message> read = messagedao3.selectReadMessage(teacher);
    List<Message> unread = messagedao4.selectReadMessage(teacher);
    model.addAttribute("list", list);
    model.addAttribute("sender", sender);
    model.addAttribute("read", read);
    model.addAttribute("unread", unread);
    return "message";
  }
  

  
  
  
  
  
  
}
