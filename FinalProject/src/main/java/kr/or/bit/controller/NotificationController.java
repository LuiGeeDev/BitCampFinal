package kr.or.bit.controller;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.Notification;

@Controller
public class NotificationController {
  
  @Autowired
  private SqlSession sqlSession;
  
  @RequestMapping("/notification")
  public String selectTest(Model model) {
    String username = "imchan";
    NotificationDao notificationdao = sqlSession.getMapper(NotificationDao.class);
    NotificationDao notificationdao2 = sqlSession.getMapper(NotificationDao.class);
    NotificationDao notificationdao3 = sqlSession.getMapper(NotificationDao.class);
    
    
    List<Notification> allnoti = notificationdao.selectAllNotification(username);
    List<Notification> newnoti = notificationdao2.selectAllNewNotification(username);
    List<Notification> oldnoti = notificationdao3.selectAllOldNotification(username);
    
    model.addAttribute("allnoti", allnoti);
    model.addAttribute("newnoti", newnoti);
    model.addAttribute("oldnoti", oldnoti);
    return "notification";
  }
  
  
  
  
}
