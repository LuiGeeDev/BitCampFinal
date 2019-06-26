package kr.or.bit.controller;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Classroom;
import kr.or.bit.model.Message;
import kr.or.bit.service.NewsService;

@RestController
@RequestMapping(path = "/ajax")
public class AjaxController {
  
  @Autowired
  private SqlSession sqlSession;
  
  @PostMapping("/news")
  public String getNews() {
    NewsService service = new NewsService();
    String news = service.getNews();
    
    return news;
  }
  
  @PostMapping("/message")
  public Message getMessage(int id) {
    System.out.println("여기탔냐?");
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    Message selectone = messageDao.selectOneMessage(id);
    return selectone;
  }



	@PostMapping("/classroom")
	public List<Classroom> getClassroom(Date start_date, @RequestParam(defaultValue = "1970-01-01") Date end_date) {
		CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
		List<Classroom> classroomList = courseDao.selectAvailableClassroom(start_date, end_date);
		for (Classroom cr : classroomList) {
			System.out.println(cr.getId() + "/" + cr.getClassroom_name());
		}
		return classroomList;
	}
}
