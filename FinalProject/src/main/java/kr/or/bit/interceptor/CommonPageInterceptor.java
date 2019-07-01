package kr.or.bit.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.Message;
import kr.or.bit.model.Notification;
import kr.or.bit.utils.Helper;

public class CommonPageInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private SqlSession sqlSession;
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView mav) throws Exception {
    
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    String username = Helper.userName();
    
    List<Message> unreadMessages = messageDao.selectUnreadMessage(username);
    List<Notification> unreadNotices = notificationDao.selectAllNewNotification(username);
    
    int unreadMessage = unreadMessages.size();
    int unreadNotice = unreadNotices.size();
    
    request.setAttribute("user", memberDao.selectMemberByUsername(username));
    request.setAttribute("unreadMessage", unreadMessage);
    request.setAttribute("unreadNotice", unreadNotice);
  }
}
