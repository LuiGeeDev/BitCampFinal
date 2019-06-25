package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;
import kr.or.bit.utils.Helper;

@Service
public class MemberService {
  @Autowired
  private SqlSession sqlSession;

  public Member getMember() {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    
    Member member = memberDao.selectMemberByUsername(username);
    return member;
  }

  public void updateMember(Member member) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    memberDao.updateMember(member);
  }

  public List<Member> getStudentsList() {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    String username = Helper.userName();
    Member member = memberDao.selectMemberByUsername(username);
 
    List<Member> memberList = memberDao.selectStudentsList(member.getCourse_id());
    return memberList;
  }
}
