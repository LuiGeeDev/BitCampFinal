package kr.or.bit.dao;
/*
*
* @date: 2019. 06. 25.
*
* @author: 권순조
*
* @description: 그룹에 속한 맴버들을 검색하거나 수정하거나 위해서
* 
*/

import java.util.List;

import kr.or.bit.model.Group;

public interface GroupDao {
  
  void insertGroup(Group group);
  
  void deleteGroup();
  
  void updateGroup();
  
  void selectAllLinkById();
  
  List<Group> selectAllGroupByProject();
  
}
