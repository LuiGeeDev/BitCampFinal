package kr.or.bit.config;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.TeacherCourseDao;
import kr.or.bit.model.Course;
import kr.or.bit.model.Member;
import kr.or.bit.model.TeacherCourse;

@Configuration
@EnableAsync
@EnableScheduling
public class ScheduledConfig {
  @Autowired
  private SqlSession sqlSession;

  /**
   * 매일 00시 00분 01초에 실행되는 Scheduled 함수
   * 
   * 강의 정보를 가져와 시작일과 종료일을 사용
   * 
   * 1. 강의 종료일과 오늘을 비교, 종료일이 지난 학생 회원은 졸업생으로 전환
   * 2. 강의 시작일과 오늘을 비교, 강의가 시작한 학생 회원은 비활성화에서 활성화 전환
   * 3. 강의 시작일과 오늘을 비교, 강의 시작 3일 전에 담당 강사를 해당 수업에 배치, 수업을 미리 준비할 수 있게 함
   * 
   * @작성자: 복다빈
   */
  @Scheduled(cron = "01 00 00 * * ?")
  public void scheduleCheckMember() {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    List<Member> memberList = memberDao.selectAllStudent();
    TeacherCourseDao teacherCourseDao = sqlSession.getMapper(TeacherCourseDao.class);

    for (int i = 0; i < memberList.size(); i++) {
      Course course = courseDao.selectCourse(memberList.get(i).getCourse_id());
      course.setStartDate(course.getStart_date().toLocalDate());
      course.setEndDate(course.getEnd_date().toLocalDate());

      if (course.getEndDate().isAfter(LocalDate.now()) && !memberList.get(i).getRole().equals("TEACHER")) {
        memberDao.updateMemberGraduate(memberList.get(i));
      }

      if (!course.getStartDate().isBefore(LocalDate.now())) {
        memberDao.updateEnabled(memberList.get(i));
      }

      Period period = Period.between(course.getStartDate(), LocalDate.now());

      if (period.getDays() < 3 && period.getDays() >= 0) {
        TeacherCourse teacherCourse = teacherCourseDao.selectTeacherCourse(memberList.get(i).getCourse_id());

        Member teacher = memberDao.selectMemberByUsername(teacherCourse.getTeacher_username());
        teacher.setCourse_id(teacherCourse.getCourse_id());

        memberDao.updateMember(teacher);
      }
    }

  }
}
