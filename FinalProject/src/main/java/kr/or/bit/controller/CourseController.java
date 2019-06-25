package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Course;
import kr.or.bit.model.Message;

@Controller
public class CourseController {
  
  @Autowired
  private SqlSession sqlsession;
  
  @RequestMapping("/course")
  public String selectTest(Model model) {
    
    CourseDao coursedao = sqlsession.getMapper(CourseDao.class);    
    List<Course> list = coursedao.selectAllCourse();
    model.addAttribute("list", list);
    
    return "course";
  }
  
  @RequestMapping("/course.insert")
  public String selectTest2(Model model) {
    
    CourseDao coursedao = sqlsession.getMapper(CourseDao.class);    
    List<Course> list = coursedao.selectAllCourse();
    model.addAttribute("list", list);
    
    return "course";
  }
  
 
  
  
}
