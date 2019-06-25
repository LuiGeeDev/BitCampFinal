package kr.or.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import kr.or.bit.model.Message;

@Controller
public class SocketController {
  @Autowired
  private SimpMessagingTemplate template;
  
  @MessageMapping("/notification")
  @SendToUser("/queue/notification")
  public void sendNotice(Message message) {
    template.convertAndSend("/topic/noti/" + message.getId(), message);
  }
  
  @MessageMapping("/notice")
  public void sendAlarm(String hello) {
    System.out.println(hello);
    template.convertAndSendToUser("manager", "/queue/notice", "new notice");
  }
}
