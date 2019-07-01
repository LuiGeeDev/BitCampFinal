package kr.or.bit.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ChatMessage;
import kr.or.bit.model.Classroom;
import kr.or.bit.model.Message;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.FileUploadService;
import kr.or.bit.service.NewsService;
import kr.or.bit.utils.Helper;

@RestController
@RequestMapping(path = "/ajax")
public class AjaxController {

  @Autowired
  private SqlSession sqlSession;
  
  @Autowired
  private ArticleService articleService;

  @PostMapping("/chat/file")
  public ChatMessage uploadFile(HttpServletRequest request, int group_id, long time, String name, MultipartFile file)
      throws IllegalStateException, IOException {
    FileUploadService service = new FileUploadService();
    String filepath = service.uploadFile(file, request);

    ChatMessage message = new ChatMessage();
    message.setUsername("fileServer");
    message.setName(name);
    message.setContent(file.getOriginalFilename());
    message.setTime(time);
    message.setGroup_id(group_id);
    message.setFilepath(filepath);

    return message;
  }

  @PostMapping("/news")
  public String getNews() {
    NewsService service = new NewsService();
    String news = service.getNews();
    return news;
  }

  @PostMapping("/message")
  public Message getMessage(int id) {
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
  public void replyMessage(Message message, HttpServletResponse response) throws IOException {
    String username = Helper.userName();
    message.setSender_username(username);
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    messageDao.insertMessage(message);
  }

  @PostMapping("/message/update")
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
  
  @PostMapping("/video/scroll")
  public List<Article> getNextVideoArticles(int article_id) {
    List<Article> list = articleService.selectArticlesOnNextPage(article_id);
    return list;
  }
}
