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
  
  @Scheduled(cron = "01 00 00 * * ?")
  public void scheduleCheckMember() {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    List<Member> memberList = memberDao.selectAllStudent();
    TeacherCourseDao teacherCourseDao  = sqlSession.getMapper(TeacherCourseDao.class);
    
    for (int i = 0 ; i < memberList.size() ; i ++) {
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
