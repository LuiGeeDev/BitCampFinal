package kr.or.bit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Course;
import kr.or.bit.model.Message;

@Controller
public class CourseController {
  
  @Autowired
  private SqlSession sqlsession;
  
  //강의목록 출력
  @RequestMapping("/course")
  public String selectTest(Model model) {
    CourseDao coursedao = sqlsession.getMapper(CourseDao.class);    
    List<Course> list = coursedao.selectAllCourse();
    model.addAttribute("list", list);
    
    return "course";
  }
  
  //insert 페이지로 이동
  @RequestMapping("/courseform")
  public String selectTest2(Model model) {
    return "courseform";
  }
  
  //insert 제출 - 테스트용임
  @RequestMapping("/courseinsert")
  public String selectTest3(Course course) {
    CourseDao coursedao = sqlsession.getMapper(CourseDao.class);  
    coursedao.insertCourse(course);
    return "course";
  }
  
  
}
