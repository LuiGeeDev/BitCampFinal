package kr.or.bit.controller;

import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.ChatMessage;
import kr.or.bit.model.Notification;

@Controller
public class SocketController {
  @Autowired
  private SimpMessagingTemplate template;
  
  @Autowired
  private SimpUserRegistry registry;
  
  @Autowired
  private SqlSession sqlSession;
  
  @MessageMapping({"/notice/vote", "/notice/comment", "/notice/reply", "/notice/message", "/notice/assemble"})
  public void sendNotice(Notification notification) {
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    notificationDao.insertNotification(notification);
    template.convertAndSendToUser(notification.getUsername(), "/queue/notice", notification);
  }
  
  @MessageMapping("/chat")
  public void sendMessage(ChatMessage chatMessage) {
    Set<SimpUser> set = registry.getUsers();
    
    for (SimpUser s : set) {
      MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
      System.out.println(memberDao.selectMemberByUsername(s.getName()).getName());
    }
    
    template.convertAndSend("/topic/chat/groups/" + chatMessage.getGroup_id(), chatMessage);
  }
}
