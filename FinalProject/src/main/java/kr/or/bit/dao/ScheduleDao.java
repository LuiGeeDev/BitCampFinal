package kr.or.bit.dao;

import kr.or.bit.model.Course;
import kr.or.bit.model.Group;
import kr.or.bit.model.Schedule;

public interface ScheduleDao {
  void insertSchedule(Schedule schedule);
  void updateSchedule(Schedule Schedule, int id);
  void deleteSchedule(int id);
  void selectAllSchedule(Course course, Group group);
}
