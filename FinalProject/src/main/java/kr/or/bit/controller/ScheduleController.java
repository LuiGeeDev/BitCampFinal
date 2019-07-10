package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ScheduleDao;
import kr.or.bit.model.Member;
import kr.or.bit.model.Schedule;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/myclass/schedule")
public class ScheduleController {

  @Autowired
  private SqlSession sqlSession;

  @GetMapping("")
  public String selectTest() {
    ScheduleDao scheduledao = sqlSession.getMapper(ScheduleDao.class);
    return "myclass";
  }
  
  @PostMapping("/insert")
  public String insertSchedule(Schedule schedule) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    schedule.setCourse_id(user.getCourse_id());
    scheduleDao.insertSchedule(schedule);
    return "myclass";
  }
  
  @PostMapping("/update")
  public String updateSchedule(Schedule schedule, int id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    scheduleDao.updateSchedule(schedule, id);
    return "myclass";
  }
  
  @GetMapping("/delete")
  public String deleteSchedule(int id) {
    ScheduleDao scheduledao = sqlSession.getMapper(ScheduleDao.class);
    scheduledao.deleteSchedule(id);
    return "myclass";
  }
}
