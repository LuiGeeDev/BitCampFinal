package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.ScheduleDao;
import kr.or.bit.model.Schedule;
import kr.or.bit.service.ScheduleService;

@Controller
@RequestMapping("/myclass/schedule")
public class ScheduleController {
  @Autowired
  private SqlSession sqlSession;

  @Autowired
  private ScheduleService service;
  
  @PostMapping("/insert/class")
  public @ResponseBody List<Schedule> insertScheduleForClass(Schedule schedule) {
    return service.insertScheduleForClass(schedule);
  }

  @PostMapping("/insert/group")
  public @ResponseBody List<Schedule> insertScheduleForGroup(Schedule schedule) {
    return service.insertScheduleForGroup(schedule);
  }
  
  @PostMapping("/update/class")
  public @ResponseBody List<Schedule> updateScheduleForClass(Schedule schedule) {
    return service.updateScheduleForClass(schedule);
  }

  @PostMapping("/update/group")
  public @ResponseBody List<Schedule> updateScheduleForGroup(Schedule schedule) {
    return service.updateScheduleForGroup(schedule);
  }
  
  @PostMapping("/delete/class")
  public @ResponseBody List<Schedule> deleteScheduleForClass(int id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    Schedule schedule = scheduleDao.selectSchedule(id);
    return service.deleteScheduleForClass(schedule);
  }

  @PostMapping("/delete/group")
  public @ResponseBody List<Schedule> deleteScheduleForGroup(int id) {
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    Schedule schedule = scheduleDao.selectSchedule(id);
    return service.deleteScheduleForGroup(schedule);
  }

  @PostMapping("/get/class")
  public @ResponseBody List<Schedule> getClassSchedule(int course_id) {
    System.out.println(service.getScheduleForClass(course_id));
    return service.getScheduleForClass(course_id);
  }

  @PostMapping("/get/group")
  public @ResponseBody List<Schedule> getClassSchedule(int course_id, int group_id) {
    return service.getScheduleForGroup(course_id, group_id);
  }
}
