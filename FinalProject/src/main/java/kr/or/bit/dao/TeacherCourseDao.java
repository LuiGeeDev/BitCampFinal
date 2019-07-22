package kr.or.bit.dao;

import kr.or.bit.model.TeacherCourse;

public interface TeacherCourseDao {
  
  TeacherCourse selectTeacherCourse(int course_id);
  
}
