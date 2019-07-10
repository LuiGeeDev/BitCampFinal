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

  public List<Schedule> insertScheduleForClass(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    schedule.setCourse_id(user.getCourse_id());
    scheduleDao.insertSchedule(schedule);
    return getScheduleForClass(user.getCourse_id());
  }

  public List<Schedule> insertScheduleForGroup(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    schedule.setCourse_id(user.getCourse_id());
    scheduleDao.insertSchedule(schedule);
    return getScheduleForGroup(user.getCourse_id(), schedule.getGroup_id());
  }

  @PreAuthorize("hasRole('TEACHER')")
  public List<Schedule> updateScheduleForClass(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    scheduleDao.updateSchedule(schedule);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    return getScheduleForClass(user.getCourse_id());
  }

  public List<Schedule> updateScheduleForGroup(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    scheduleDao.updateSchedule(schedule);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    return getScheduleForGroup(user.getCourse_id(), schedule.getGroup_id());
  }

  @PreAuthorize("hasRole('TEACHER')")
  public List<Schedule> deleteScheduleForClass(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    scheduleDao.deleteSchedule(schedule.getId());
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    return getScheduleForClass(user.getCourse_id());
  }

  public List<Schedule> deleteScheduleForGroup(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    scheduleDao.deleteSchedule(schedule.getId());
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    return getScheduleForGroup(user.getCourse_id(), schedule.getGroup_id());
  }


  public List<Schedule> getScheduleForClass(int course_id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    List<Schedule> schedules = scheduleDao.selectScheduleForClass(course_id);
    for (Schedule sc : schedules) {
      sc.setStartLocal(sc.getStart_date().toLocalDate());
      sc.setEndLocal(sc.getEnd_date().toLocalDate());
    }
    return schedules;
  }

  public List<Schedule> getScheduleForGroup(int course_id, int group_id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    List<Schedule> schedules = scheduleDao.selectScheduleForGroup(course_id, group_id);
    for (Schedule sc : schedules) {
      sc.setStartLocal(sc.getStart_date().toLocalDate());
      sc.setEndLocal(sc.getEnd_date().toLocalDate());
    }
    return schedules;
  }

}
