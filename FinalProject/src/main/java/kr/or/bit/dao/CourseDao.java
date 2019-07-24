package kr.or.bit.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Classroom;
import kr.or.bit.model.Course;
import kr.or.bit.model.Member;
import kr.or.bit.utils.Pager;

/*
*
* @date: 2019. 06. 21.
*
* @author: 권예지
*
* @description: CourseDao를 통해서 Course CRUD를 이용한다
* 
*/
public interface CourseDao {
  void insertCourse(Course course);

  void updateCourse(Course course); // CourseDTO의 id

  void deleteCourse(int id);

  List<Course> selectAllCourse();

  Course selectCourse(int id);

  Course selectRecentCourse();

  List<Classroom> selectAvailableClassroom(@Param("start_date") Date start_date, @Param("end_date") Date end_date);

  List<Member> selectAvailableTeacher(@Param("start_date") Date start_date, @Param("end_date") Date end_date);

  List<Course> selectAllTeacherCourse(String username);

  //전체 클래스 리스트
  List<Course> selectAllCourseList();

  //진행중인 클래스 리스트
  List<Course> selectCurrentCourseList();

  //종료된 클래스 리스트
  List<Course> selectEndCourseList(@Param("pager") Pager pager);

  //종료된 클래스 개수
  int countEndCourseList();

  //개강예정 클래스 리스트
  List<Course> selectOpeningCourseList();

}
