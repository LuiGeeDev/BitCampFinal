package kr.or.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import kr.or.bit.model.Message;

@Controller
public class SocketController {
  @Autowired
  private SimpMessagingTemplate template;
  
  @MessageMapping("/noti")
  public void sendNotice(Message message) {
    template.convertAndSend("/topic/noti/" + message.getBoardWriter(), message);
  }
}
