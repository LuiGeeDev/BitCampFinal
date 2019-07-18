package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.ChatMessage;
import kr.or.bit.model.Member;
import kr.or.bit.model.Notification;
import kr.or.bit.utils.Helper;

@Controller
public class SocketController {
  @Autowired
  private SimpMessagingTemplate template;
  
  @Autowired
  private SimpUserRegistry registry;
  
  @Autowired
  private SqlSession sqlSession;
  
  @MessageMapping({"/notice/vote", "/notice/comment", "/notice/reply"})
  public void sendNotice(Notification notification) {
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    notificationDao.insertNotification(notification);
    template.convertAndSendToUser(notification.getUsername(), "/queue/notice", notification);
  }
  
  @MessageMapping("/notice/message")
  public void sendMessageNotice(Notification notification) {
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    notificationDao.insertNotification(notification);
    template.convertAndSendToUser(notification.getUsername(), "/queue/message", notification);
  }
  
  @MessageMapping("/notice/assemble")
  public void sendAssembleNotice(Notification notification) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    
    Member teacher = memberDao.selectMemberByUsername(notification.getUsername());
    List<Member> students = memberDao.selectAllMembersByMycourse(teacher.getCourse_id());
    
    for (Member student : students) {
      template.convertAndSendToUser(student.getUsername(), "/queue/notice", notification);
      notification.setUsername(student.getUsername());
      notificationDao.insertNotification(notification);
    }
  }
  
  @MessageMapping("/chat")
  public void sendMessage(ChatMessage chatMessage) {
    template.convertAndSend("/topic/chat/groups/" + chatMessage.getGroup_id(), chatMessage);
  }
}
