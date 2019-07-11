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

  void updateSchedule(Schedule schedule);

  void deleteSchedule(int id);

  Schedule selectSchedule(int id);

  List<Schedule> selectScheduleForClass(int course_id);

  List<Schedule> selectScheduleForGroup(@Param("course_id") int course_id, @Param("group_id") int group_id);
}
