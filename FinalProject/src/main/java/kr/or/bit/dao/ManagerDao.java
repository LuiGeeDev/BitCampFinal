package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Classroom;
import kr.or.bit.model.Comment;
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
 */
import kr.or.bit.model.Subject; 
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
  
  void updateMemberEnabled(@Param("enabled") int enabled, @Param("username") String username);
  
  //chart
  //과목별 클래스 개수
  List<Course> countCourseBySubject();
  //모든 클래스 개수
  int countAllCourse();
  //클래스별 중탈자 개수
  List<Course> countEnableMember();
  //글가장 많이 쓴 수
  List<Course> articleWriteRank();
  //댓글 가장 많이 쓴 수
  List<Comment> commentWriteRank();
  //클래스 리스트
  List<Course> selectCourseList();
  //강사 리스트
  List<Member> selectTeacherList();
  //과목 리스트
  List<Subject> selectSubjectList();
  //강의실 리스트
  List<Classroom> selectClassroomList();
}
