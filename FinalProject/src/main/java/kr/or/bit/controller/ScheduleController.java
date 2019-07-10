package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.ScheduleDao;

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
  public String insertSchedule() {
    return "myclass";
  }
  @PostMapping("/update")
  public String updateSchedule() {
    
    return "myclass";
  }
  @PostMapping("/delete")
  public String deleteSchedule(int id) {
    ScheduleDao scheduledao = sqlSession.getMapper(ScheduleDao.class);
    scheduledao.deleteSchedule(id);
    return "myclass";
  }
}
