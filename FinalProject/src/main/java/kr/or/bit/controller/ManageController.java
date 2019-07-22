package kr.or.bit.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.ManagerDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Classroom;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Course;
import kr.or.bit.model.Member;
import kr.or.bit.model.Subject;
import kr.or.bit.service.ClassCreateService;
import kr.or.bit.service.MailService;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Controller
@RequestMapping("/manage")
public class ManageController {
	@Autowired
	private ClassCreateService classCreateService;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private MailService mailService;

	@GetMapping("/course")
	public String manageHome(Model model, @RequestParam(defaultValue = "1") int page) {
	  ManagerDao managerDao = sqlSession.getMapper(ManagerDao.class);
	  CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
	  Pager pager = new Pager(page, courseDao.countEndCourseList());
	  
	  List<Member> teacherList = managerDao.selectTeacherList();
	  List<Subject> subjectList = managerDao.selectSubjectList();
	  List<Classroom> classroomList = managerDao.selectClassroomList();
	  List<Course> currentCourseList = courseDao.selectCurrentCourseList();
	  List<Course> openingCourseList = courseDao.selectOpeningCourseList();
	  List<Course> endCourseList = courseDao.selectEndCourseList(pager);
	  model.addAttribute("currentCourseList", currentCourseList);
	  model.addAttribute("endCourseList", endCourseList);
	  model.addAttribute("openingCourseList", openingCourseList);
	  model.addAttribute("teacherList", teacherList);
	  model.addAttribute("subjectList", subjectList);
	  model.addAttribute("classroomList", classroomList);
	  model.addAttribute("pager", pager);
	  model.addAttribute("page", page);
		return "manage/course";
	}

	@PostMapping("/check/id")
	public @ResponseBody boolean checkIdDuplicates(String username) {
		MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		Member member = memberDao.selectMemberByUsername(username);
		return (member != null);
	}

	@PostMapping("/create/teacher")
	public String createNewTeacher(Member member) throws MessagingException {
		MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		member.setPassword(Helper.defaultPassword());
		memberDao.insertTeacher(member);
		mailService.sendNewTeacherEmail(member.getEmail(), member.getName());
		return "redirect:/manage/course";
	}

	@PostMapping("/createClass")
	public String createClass(Course course, @RequestParam(required = true) int people, @RequestParam int teacher_id,
			Model model) {
		classCreateService.createClass(course, people, teacher_id);

		return "redirect:/manage/course";
	}

	@GetMapping("/students")
	public String manageStudentHome(Model model) {
		ManagerDao managerDao = sqlSession.getMapper(ManagerDao.class);
		CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
		List<Member> memberList = managerDao.selectMembersByRole("STUDENT");
		List<Course> courseList = courseDao.selectAllCourseList();
		model.addAttribute("courseList", courseList);
		model.addAttribute("memberList", memberList);
		return "manage/students";
	}

	@PostMapping("/students")
	public String manageStudentSearch(String role, int enabled, int course_id, String stringColumn,
			@RequestParam(defaultValue = "null") String stringValue, Model model) {
		ManagerDao managerDao = sqlSession.getMapper(ManagerDao.class);
		CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
		List<Member> memberList = null;

		if (enabled == 999 & course_id == 0 & stringValue.equals("null")) {
			memberList = managerDao.selectMembersByRole(role);
		} else if (enabled == 999 & course_id == 0) {
			memberList = managerDao.selectMembersByRoleAndOneStringColumn(role, stringColumn, stringValue);
		} else if (enabled == 999 & stringValue.equals("null")) {
			memberList = managerDao.selectMembersByRoleAndOneIntColumn(role, "course_id", course_id);
		} else if (course_id == 0 & stringValue.equals("null")) {
			memberList = managerDao.selectMembersByRoleAndOneIntColumn(role, "enabled", enabled);
		} else if (stringValue.equals("null")) {
			memberList = managerDao.selectMembersByRoleAndEnableAndCourseId(role, enabled, course_id);
		} else if (enabled == 999) {
			memberList = managerDao.selectMembersByRoleAndStringColumnAndIntColumn(role, stringColumn, stringValue,
					"course_id", course_id);
		} else if (course_id == 0) {
			memberList = managerDao.selectMembersByRoleAndStringColumnAndIntColumn(role, stringColumn, stringValue, "enabled",
					enabled);
		} else {
			memberList = managerDao.selectMemberByRoleAndEnableAndCourseIdAndSearch(role, enabled, course_id, stringColumn,
					stringValue);
		}

		List<Course> courseList = courseDao.selectAllCourseList();
		model.addAttribute("courseList", courseList);
		model.addAttribute("memberList", memberList);
		return "manage/students";
	}

	@GetMapping("/chart")
	public String memberChartPage(Model model) {
	  ManagerDao managerDao = sqlSession.getMapper(ManagerDao.class);
	  CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
	  
	  List<Course> chartOne = managerDao.countCourseBySubject();
    int courseCount = managerDao.countAllCourse();
    for(Course course : chartOne) {
      double result = (double)course.getCount()/(double)courseCount;
      course.setDivisionResult(Math.round(result*1000)/10.0);
    }
    
    List<Course> chartTwo = managerDao.countEnableMember();
    List<Course> chartThree = managerDao.articleWriteRank();
    List<Comment> chartFour = managerDao.commentWriteRank();
    List<Course> courseList = courseDao.selectAllCourseList();
    
    model.addAttribute("chartTwo", chartTwo);
    model.addAttribute("chartOne", chartOne);
    model.addAttribute("chartThree", chartThree);
    model.addAttribute("chartFour", chartFour);
    model.addAttribute("courseList", courseList);
	  return "manage/chart";
	}

}
