package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.ChatMessage;
import kr.or.bit.model.Member;
import kr.or.bit.model.Notification;

@Controller
public class SocketController {
  @Autowired
  private SimpMessagingTemplate template;

  @Autowired
  private SqlSession sqlSession;

  /**
   * sendNotice, sendMessageNotice
   * 
   * @param notification
   * 
   * @MessageMapping 안의 주소로 온 웹소켓 요청을 받아 해당하는 유저에게 메시지 전달
   */
  @MessageMapping({ "/notice/vote", "/notice/comment", "/notice/reply" })
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

  /**
   * @param notification
   * 
   * 모여라 알림이 올 경우 강사의 수업을 듣는 모든 학생 데이터를 가져와
   * 모든 학생에게 모여라 알림을 보냄
   */
  @MessageMapping("/notice/assemble")
  public void sendAssembleNotice(Notification notification) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);

    Member teacher = memberDao.selectMemberByUsername(notification.getUsername());
    List<Member> students = memberDao.selectAllMembersByMycourse(teacher.getCourse_id());

    for (Member student : students) {
      System.out.println(student.getUsername());
      template.convertAndSendToUser(student.getUsername(), "/queue/notice", notification);
      notification.setUsername(student.getUsername());
      notificationDao.insertNotification(notification);
    }
  }

  /**
   * 채팅에서 사용하는 웹소켓 함수, 채팅에 접속한 모든 유저에게 메시지 전달
   * 
   * @param chatMessage
   */
  @MessageMapping("/chat")
  public void sendMessage(ChatMessage chatMessage) {
    template.convertAndSend("/topic/chat/groups/" + chatMessage.getGroup_id(), chatMessage);
  }
}
