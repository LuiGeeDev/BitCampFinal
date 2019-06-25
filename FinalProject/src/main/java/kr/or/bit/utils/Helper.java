package kr.or.bit.utils;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;

public class Helper {
  
   public static Member user(SqlSession sqlsession) {//로그인된 사람의 맴버 객체를 뽑아주는 함수
     MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
     
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     String username = userDetails.getUsername();
     
     Member user = memberDao.selectMemberByUsername(username);
     
     return user;
   }
}
