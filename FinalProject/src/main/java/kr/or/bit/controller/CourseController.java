package kr.or.bit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Course;
import kr.or.bit.model.Message;

@Controller
public class CourseController {

  @Autowired
  private SqlSession sqlsession;

  // 강의목록 출력
  @RequestMapping("/course")
  public String selectTest(Model model) {
    CourseDao coursedao = sqlsession.getMapper(CourseDao.class);
    List<Course> list = coursedao.selectAllCourse();
    model.addAttribute("list", list);

    return "course";
  }

  // insert 페이지로 이동
  @GetMapping("/courseinsert")
  public String selectTest2(Model model) {
    return "courseform";
  }

  // insert 제출 - 테스트용임
  @PostMapping("/courseinsert")
  public String selectTest3(Course course) {
    CourseDao coursedao = sqlsession.getMapper(CourseDao.class);
    coursedao.insertCourse(course);
    return "course";
  }

  // update 페이지로 이동
  @GetMapping("/courseupdate")
  public String selectTest4(HttpServletRequest request, Model model) {
    int id = Integer.parseInt(request.getParameter("id"));
    CourseDao coursedao = sqlsession.getMapper(CourseDao.class);
    Course editcourse = coursedao.selectCourse(id);
    model.addAttribute("course", editcourse);
    return "courseedit";
  }

  /*
   * //update 페이지로 이동
   * 
   * @PostMapping("/courseupdate") public String selectTest4(HttpServletRequest
   * request, Course course) { int id =
   * Integer.parseInt(request.getParameter("id")); CourseDao coursedao =
   * sqlsession.getMapper(CourseDao.class); Course editcourse =
   * coursedao.selectCourse(id); return "courseedit?id=id"; }
   */

}
