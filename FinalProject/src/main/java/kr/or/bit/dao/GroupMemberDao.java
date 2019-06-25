package kr.or.bit.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Member;

/*
*
* @date: 2019. 06. 25.
*
* @author: 권순조
*
* @description: 그룹에 속한 맴버들을 검색하거나 수정하거나 위해서
* 
*/

public interface GroupMemberDao {

  void insertGroupMember(Member member);

  void updateGroupMember(Member member);

  void deleteGroupMember(String username);

  List<Member> selectAllGroupMemberByGroup();
}
