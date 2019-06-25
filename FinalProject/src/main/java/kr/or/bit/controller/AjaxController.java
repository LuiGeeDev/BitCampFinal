package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Message;
import kr.or.bit.service.NewsService;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
  
  @Autowired
  private SqlSession sqlsession;
  
  @PostMapping("/news")
  public String getNews() {
    NewsService service = new NewsService();
    String news = service.getNews();
    
    return news;
  }
  
  @PostMapping("/message")
  public Message getMessage(int id) {
    System.out.println("여기탔냐?");
    MessageDao messageDao = sqlsession.getMapper(MessageDao.class);
    Message selectone = messageDao.selectOneMessage(id);
    return selectone;
  }
}
