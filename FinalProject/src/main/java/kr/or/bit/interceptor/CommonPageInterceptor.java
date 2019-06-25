package kr.or.bit.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.bit.dao.MessageDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.Message;
import kr.or.bit.model.Notification;

public class CommonPageInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private SqlSession sqlSession;
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView mav) throws Exception {
    
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    
    List<Message> unreadMessages = messageDao.selectUnreadMessage(username);
    List<Notification> unreadNotices = notificationDao.selectAllNewNotification(username);
    
    int unreadMessage = unreadMessages == null ? 0 : unreadMessages.size();
    int unreadNotice = unreadNotices == null ? 0 : unreadNotices.size();
    
    mav.addObject("unreadMessage", unreadMessage);
    mav.addObject("unreadNotice", unreadNotice);
  }
}
