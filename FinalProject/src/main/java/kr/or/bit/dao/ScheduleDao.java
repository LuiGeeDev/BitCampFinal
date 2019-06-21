package kr.or.bit.dao;

import kr.or.bit.model.Course;
import kr.or.bit.model.Group;
import kr.or.bit.model.Schedule;
/*
*
* @date: 2019. 06. 21.
*
* @author: 정성윤
*
* @description: ScheduleDao의 CRUD
*
*/

public interface ScheduleDao {
  void insertSchedule(Schedule schedule);

  void updateSchedule(Schedule Schedule, int id);

  void deleteSchedule(int id);

  void selectAllSchedule(Course course, Group group);
}
