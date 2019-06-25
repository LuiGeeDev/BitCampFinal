package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;

@Service
public class MemberService {
  @Autowired
  private SqlSession sqlSession;

  public Member getMember() {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = userDetails.getUsername();
    Member member = memberDao.selectMemberByUsername(username);
    return member;
  }

  public void updateMember(Member member) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    memberDao.updateMember(member);
  }

  public List<Member> getAllMembers() {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Member> memberList = memberDao.selectAllMembers();
    return memberList;
  }
}
