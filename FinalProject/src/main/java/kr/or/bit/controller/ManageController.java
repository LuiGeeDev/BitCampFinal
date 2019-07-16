package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.ManagerDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Course;
import kr.or.bit.model.Member;
import kr.or.bit.service.ClassCreateService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	@Autowired
	private ClassCreateService classCreateService;
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("/course")
	public String manageHome() {
		return "manage/course";
	}
	
	@PostMapping("/createClass")
	public String createClass(Course course, @RequestParam(required = true) int people, @RequestParam int teacher_id, Model model) {
		classCreateService.createClass(course, people, teacher_id);
		
		return "redirect:/manage/course";
	}
	
	@GetMapping("/students")
	public String manageStudentHome(Model model) {
	  ManagerDao managerDao = sqlSession.getMapper(ManagerDao.class);
	  CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
	  List<Member> memberList = managerDao.selectMembersByRole("STUDENT");
	  List<Course> courseList = managerDao.selectCourseList();
	  System.out.println(courseList.get(0).getCourse_name());
	  System.out.println(courseList.get(1).getCourse_name());
	  System.out.println(courseList.get(2).getCourse_name());
	  System.out.println(courseList.get(3).getCourse_name());
    model.addAttribute("courseList", courseList);
	  model.addAttribute("memberList", memberList);
	  return "manage/students";
	}
	
	@PostMapping("/students")
	public String manageStudentSearch(String role, int enabled, int course_id, String stringColumn, @RequestParam(defaultValue="null") String stringValue, Model model) {
	  ManagerDao managerDao = sqlSession.getMapper(ManagerDao.class);
	  List<Member> memberList = null;
	  
	  System.out.println(role+"/"+enabled+"/"+course_id+"/"+stringColumn+"/"+stringValue);
	  
	  if(enabled == 999 & course_id ==0 & stringValue.equals("null")) {
	    System.out.println("role?");
	    memberList = managerDao.selectMembersByRole(role);
	  } else if (enabled==999 & course_id==0) {
      System.out.println("searchColumn 탐?");
      memberList = managerDao.selectMembersByRoleAndOneStringColumn(role, stringColumn, stringValue);
    } else if (enabled==999 & stringValue.equals("null")) {
      System.out.println("course_id");
      memberList = managerDao.selectMembersByRoleAndOneIntColumn(role, "course_id", course_id);
    } else if (course_id==0 & stringValue.equals("null")) {
      System.out.println("enabled");
      memberList = managerDao.selectMembersByRoleAndOneIntColumn(role, "enabled", enabled);
    } else if (stringValue.equals("null")) {
      memberList = managerDao.selectMembersByRoleAndEnableAndCourseId(role, enabled, course_id);
    } else if (enabled==999) {
      memberList = managerDao.selectMembersByRoleAndStringColumnAndIntColumn(role, stringColumn, stringValue, "course_id", course_id);
    } else if (course_id==0) {
      memberList = managerDao.selectMembersByRoleAndStringColumnAndIntColumn(role, stringColumn, stringValue, "enabled", enabled);
    } else {
      memberList = managerDao.selectMemberByRoleAndEnableAndCourseIdAndSearch(role, enabled, course_id, stringColumn, stringValue);
    }
	  
	  System.out.println("--------------------");
	  System.out.println(memberList.toString());
	  
	  List<Course> courseList = managerDao.selectCourseList();
	  System.out.println(courseList.get(0).getCourse_name());
	  System.out.println(courseList.get(1).getCourse_name());
	  System.out.println(courseList.get(2).getCourse_name());
	  System.out.println(courseList.get(3).getCourse_name());
	  model.addAttribute("courseList", courseList);
	  model.addAttribute("memberList", memberList);
	  return"manage/students";
	}
	
	@GetMapping("/chart")
	public String memberChartPage(Model model) {
	  ManagerDao managerDao = sqlSession.getMapper(ManagerDao.class);
	  
	  List<Course> chartOne = managerDao.countCourseBySubject();
    int courseCount = managerDao.countAllCourse();
    for(Course course : chartOne) {
      double result = (double)course.getCount()/(double)courseCount;
      System.out.println("결과 : "+(Math.round(result*1000)/10.0)+"%");
      course.setDivisionResult(Math.round(result*1000)/10.0);
    }
    
    List<Course> chartTwo = managerDao.countEnableMember();
    List<Course> chartThree = managerDao.articleWriteRank();
    List<Comment> chartFour = managerDao.commentWriteRank();
    List<Course> courseList = managerDao.selectCourseList();
    
    model.addAttribute("chartTwo", chartTwo);
    model.addAttribute("chartOne", chartOne);
    model.addAttribute("chartThree", chartThree);
    model.addAttribute("chartFour", chartFour);
    model.addAttribute("courseList", courseList);
	  return "manage/chart";
	}

}
