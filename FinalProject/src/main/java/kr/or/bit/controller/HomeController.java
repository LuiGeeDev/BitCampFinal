package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Member;
import kr.or.bit.model.Message;
import kr.or.bit.utils.Helper;

@Controller
public class HomeController {
  private final int STACK_BOARD_ID = 1;
  private final int VIDEO_BOARD_ID = 2;
  
  @Autowired
  private SqlSession sqlSession;

  @GetMapping("/")
  public String home(Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);

    String username = Helper.userName();

    Member user = memberDao.selectMemberByUsername(username);
    List<Message> mainMessage = messageDao.selectMainMessage(username);
    List<Article> recentVideos = articleDao.selectAllArticleByBoardId(VIDEO_BOARD_ID);
    List<Article> recentStacks = articleDao.selectAllArticleByBoardId(STACK_BOARD_ID);
    for (Article video : recentVideos) {
      video.setOption(videoDao.selectVideoByArticleId(video.getId()));
    }
    
    for (Article stack : recentStacks) {
      stack.setTimeLocal(stack.getTime().toLocalDateTime());
      stack.setWriter(memberDao.selectMemberByUsername(stack.getUsername()));
    }
    
    for (Message m : mainMessage) {
      m.setTimeLocal(m.getTime().toLocalDateTime());
      m.setSenderName(memberDao.selectMemberByUsername(m.getSender_username()).getName());
    }

    model.addAttribute("user", user);
    model.addAttribute("mainMessage", mainMessage);
    model.addAttribute("recentVideos", recentVideos);
    model.addAttribute("recentStacks", recentStacks);
    return "main";
  }

  @GetMapping("/menu")
  public String menu() {
    return "menu";
  }
}

