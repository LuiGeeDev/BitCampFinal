package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Course;
import kr.or.bit.service.ClassCreateService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private ClassCreateService classCreateService;
	
	@GetMapping("/home")
	public String manageHome() {
		return "manage/home";
	}
	
	@PostMapping("/createClass")
	public String createClass(Course course, @RequestParam(required = true) int people, @RequestParam int teacher_id, Model model) {
		classCreateService.createClass(course, people, teacher_id);
		
		return "redirect:/manage/home";
	}
	
	@GetMapping("/students")
	public String manageStudentHome() {
	  
	  return "manage/students";
	}
}
