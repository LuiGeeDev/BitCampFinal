package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.dao.ScheduleDao;
import kr.or.bit.model.Member;
import kr.or.bit.model.Message;

@Controller
public class HomeController {
  @Autowired
  private SqlSession sqlSession;
  
  @GetMapping("/")
  public String home(Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    
    MessageDao messageDao2 = sqlSession.getMapper(MessageDao.class);
    
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    
    Member user = memberDao.selectMemberByUsername(username);
    int unreadMessage = messageDao.selectUnreadMessage(username).size();
    int unreadNotice = notificationDao.selectAllNewNotification(username).size();
    
    System.out.println(messageDao2.selectMainMessage(username));
    List<Message> selectMainMessage = messageDao2.selectMainMessage(username);
    
	  model.addAttribute("user", user);
	  model.addAttribute("unreadMessage", unreadMessage);
	  model.addAttribute("unreadNotice", unreadNotice);
	  model.addAttribute("selectMainMessage", selectMainMessage);
	  
    return "main";
  }

  @GetMapping("/menu")
  public String menu() {
    return "menu";
  }
}
