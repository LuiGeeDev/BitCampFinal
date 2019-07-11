package kr.or.bit.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.or.bit.model.Schedule;
import kr.or.bit.service.ScheduleService;

@Controller
@RequestMapping("/myclass/schedule")
public class ScheduleController {
  @Autowired
  private ScheduleService service;
  
  @PostMapping("/insert")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void insertScheduleForClass(Schedule schedule) {
    service.insertSchedule(schedule);
  }
  
  @PostMapping("/update")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateScheduleForClass(Schedule schedule) {
    service.updateSchedule(schedule);
  }

  @PostMapping("/change-date")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changeDates(Schedule schedule) {
    service.changeDates(schedule);
  }

  @PostMapping("/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteScheduleForClass(int id) {
    service.deleteSchedule(id);
  }

  @PostMapping("/get/class")
  public @ResponseBody List<Schedule> getClassSchedule(int course_id) {
    return service.getScheduleForClass(course_id);
  }

  @PostMapping("/get/group")
  public @ResponseBody List<Schedule> getGroupSchedule(int course_id, int group_id) {
    return service.getScheduleForGroup(course_id, group_id);
  }
}
