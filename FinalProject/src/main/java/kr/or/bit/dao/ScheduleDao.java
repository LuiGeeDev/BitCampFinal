package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

  void updateSchedule(Schedule schedule, int id);

  void deleteSchedule(int id);

  List<Schedule> selectAllSchedule(Course course, Group group);
}
