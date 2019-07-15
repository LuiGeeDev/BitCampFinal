package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.ChatMessage;

@Controller
public class ChatController {
  @Autowired
  private SimpMessagingTemplate template;

  @Autowired
  private SimpUserRegistry registry;

  @Autowired
  private SqlSession sqlSession;
  
  @MessageMapping("/chat/send")
  public void sendMessage(ChatMessage chatMessage) {
    template.convertAndSend("/topic/chat/groups/" + chatMessage.getGroup_id(), chatMessage);
  }

  @MessageMapping({ "/chat/in", "/chat/out" })
  public void changeLoggedStatus(int group_id) {
    System.out.println(group_id);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Set<SimpUser> set = registry.getUsers();
    List<String> loggedMembers = new ArrayList<>();

    for (SimpUser s : set) {
      loggedMembers.add(memberDao.selectMemberByUsername(s.getName()).getName());
    }

    template.convertAndSend("/topic/chat/groups/member/" + group_id, loggedMembers);
  }
  
  @EventListener
  public void loggedOut(SessionDisconnectEvent event) {
    System.out.println(event.getUser().getName() + " Disconnected");
  }
}