package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Course;
import kr.or.bit.model.Member;
/*
 * 
 * @date: 2019. 07. 11. 
 *
 * @author: 이힘찬 
 * 
 * @description: MemberDaoMapper 
 * 
 * 검색기능
 * role          : 강사/학생/졸업자
 * enable        : 전체/활성/비활성
 * course_id     : 전체/반아이디
 * name,username : text 검색
 * 
 */ 
public interface ManagerDao {
  //role은 기본선택
  List<Member> selectMembersByRole(String role);
  //enable or course_id 으로 검색  (int하나)
  List<Member> selectMembersByRoleAndOneIntColumn(@Param("role") String role, @Param("intColumn") String intColumn, @Param("intValue") int intValue);
  //name,username 으로 검색 (String하나)
  List<Member> selectMembersByRoleAndOneStringColumn(@Param("role") String role, @Param("stringColumn") String stringColumn, @Param("stringValue") String stringValue);
  //enable And course_id 으로 검색 (int 두개)
  List<Member> selectMembersByRoleAndEnableAndCourseId(@Param("role") String role, @Param("enabled") int enabled, @Param("course_id") int course_id);
  //name,username (String) , enable or course_id (int) 으로 검색
  List<Member> selectMembersByRoleAndStringColumnAndIntColumn(@Param("role") String role, @Param("stringColumn") String stringColumn, @Param("stringValue") String stringValue, @Param("intColumn") String intColumn, @Param("intValue") int intValue);
  //enable (int) , course_id (int) , name,username (String) 으로 검색 
  List<Member> selectMemberByRoleAndEnableAndCourseIdAndSearch(@Param("role") String role, @Param("enabled") int enabled, @Param("course_id") int course_id, @Param("stringColumn") String stringColumn, @Param("stringValue") String stringValue);
  
  List<Course> selectCourseList();
  
  void updateMemberEnabled(@Param("enabled") int enabled, @Param("username") String username);
}
