package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ScheduleDao;
import kr.or.bit.model.Member;
import kr.or.bit.model.Schedule;
import kr.or.bit.utils.Helper;

@Service
public class ScheduleService {
  @Autowired
  private SqlSession sqlSession;

  public void insertSchedule(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    schedule.setCourse_id(user.getCourse_id());
    scheduleDao.insertSchedule(schedule);
  }

  @PreAuthorize("hasRole('TEACHER')")
  public void updateSchedule(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);    
    scheduleDao.updateSchedule(schedule);
  }

  @PreAuthorize("hasRole('TEACHER')")
  public void deleteSchedule(int id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    scheduleDao.deleteSchedule(id);
  }

  public List<Schedule> getScheduleForClass(int course_id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    List<Schedule> schedules = scheduleDao.selectScheduleForClass(course_id);
    for (Schedule sc : schedules) {
      sc.setStartLocal(sc.getStart().toLocalDate());
      sc.setEndLocal(sc.getEnd().toLocalDate());
    }
    return schedules;
  }

  public List<Schedule> getScheduleForGroup(int course_id, int group_id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    List<Schedule> schedules = scheduleDao.selectScheduleForGroup(course_id, group_id);
    for (Schedule sc : schedules) {
      sc.setStartLocal(sc.getStart().toLocalDate());
      sc.setEndLocal(sc.getEnd().toLocalDate());
    }
    return schedules;
  }

}
