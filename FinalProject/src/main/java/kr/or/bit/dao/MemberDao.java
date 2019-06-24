package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Member;

/*
*
* @date: 2019. 6. 21.
*
* @author: 이힘찬 
*
* @description: MemberDao 
* 
*/
public interface MemberDao {
  void insertMember(Member member);

  void updateMember(Member member);

  void deleteMember(String username);

  List<Member> selectAllMembers();

  Member selectMemberByUsername(String username);
}
