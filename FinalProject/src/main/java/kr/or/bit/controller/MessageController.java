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
  
  @RequestMapping("/message.htm")
  public String insertMessage(String teacher, Model model) {
    MessageDao messagedao = sqlsession.getMapper(MessageDao.class);
    // Message message = new Message();
    List<Message> list = messagedao.selectAllMessage(teacher);
    
    System.out.println(list);
    model.addAttribute(list);
       
    
    return "message.htm";
    
  }
  
}
