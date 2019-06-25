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

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("/home")
	public String manageHome() {
		return "manage/home";
	}
	
	@PostMapping("/createClass")
	@Transactional
	public String createClass(Course course, @RequestParam(required = true) int people, @RequestParam int teacher_id, Model model) {
		String defaultPassword = "$2a$10$L1pWhHeMtfEafgAFLR9iUO/gbTZFFoqFMMAWQ7RRDaKVd88kO92Ve"; // bitcamp
		CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
		MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		courseDao.insertCourse(course);
		Course newCourse = courseDao.selectRecentCourse();
		memberDao.insertNewCourseMembers(people, teacher_id, newCourse.getId(), newCourse.getStart_date(), defaultPassword);
		
		return "redirect:/manage/home";
	}
}
