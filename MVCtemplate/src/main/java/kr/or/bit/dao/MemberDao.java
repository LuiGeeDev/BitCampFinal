package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Member;

public interface MemberDao {
  List<Member> selectAllMembers();
  Member selectMember(String id);
  void join(Member member);
}
