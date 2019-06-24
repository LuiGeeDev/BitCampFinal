package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.ScheduleDao;
import kr.or.bit.model.Schedule;

@Controller
public class ScheduleController {
  
  @Autowired
  private SqlSession sqlSession;
  
  
  @RequestMapping("/schedule")
  public String selectTest() {
    ScheduleDao scheduledao = sqlSession.getMapper(ScheduleDao.class);
    List<Schedule> list = scheduledao.selectAllSchedule(course, group);
    
    
    
    return "schedule";
  }
}
