package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Course;
import kr.or.bit.utils.Helper;

@Service
public class ClassCreateService {
  @Autowired
  private SqlSession sqlSession;

  @Transactional
  public void createClass(Course course, int people, int teacher_id) {
    String defaultPassword = Helper.defaultPassword(); // bitcamp
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    // 강의 생성
    courseDao.insertCourse(course);
    Course newCourse = courseDao.selectRecentCourse();
    // 강의 학생 생성
    memberDao.insertNewCourseMembers(people, teacher_id, newCourse.getId(), newCourse.getStart_date(), defaultPassword);
    // 강의 게시판 생성 DB에서 진행s
  }
}