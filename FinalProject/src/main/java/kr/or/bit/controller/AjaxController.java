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
import kr.or.bit.utils.Helper;

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
    System.out.println("탓어?");
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    Message selectone = messageDao.selectOneMessage(id);
    return selectone;
  }

  @PostMapping("/message/delete") 
  public String deleteMessage(int id) {
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    messageDao.deleteMessage(id);
    return "redirect:/message";
    
  }
  
  @PostMapping("/message/reply")
  public String replyMessage(Message message) {
    String username = Helper.userName();
    message.setSender_username(username);
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    messageDao.insertMessage(message);
    return "redirect:/message";
    
  }
  
  
  @PostMapping("message/update") 
   public String updateMessage(int id) {
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    messageDao.updateMessageChecked(id);
    return "redirect:/message";
    
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
