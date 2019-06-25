package kr.or.bit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;

public class PasswordChangedInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private SqlSession sqlSession;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Member user = memberDao.selectMemberByUsername(username);

    if (bCryptPasswordEncoder.matches("bitcamp", user.getPassword())) {
      request.setAttribute("error", "default-password");
      response.sendRedirect(request.getContextPath() + "/mypage");
      return false;
    }
    
    return true;
  }
}
