package kr.or.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.model.Course;
import kr.or.bit.service.ClassCreateService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	@Autowired
	private ClassCreateService classCreateService;
	
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
	public String manageStudentHome() {
	  
	  return "manage/students";
	}
}
