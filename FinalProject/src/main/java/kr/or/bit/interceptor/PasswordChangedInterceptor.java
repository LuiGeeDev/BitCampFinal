package kr.or.bit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;
import kr.or.bit.utils.Helper;

/*
 * 사용자 최초 로그인 시 발생하는 Interceptor
 * 
 * 최초 지정된 비밀번호 ('bitcamp') 에서 비밀번호를 변경하지 않았다면 다른 페이지를 이용할 수 없게함.
 * */

public class PasswordChangedInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private SqlSession sqlSession;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String username = Helper.userName();
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
