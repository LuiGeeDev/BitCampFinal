package kr.or.bit.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Board;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Member;

@Controller
public class HomeController {
  @Autowired
  private SqlSession sqlsession;

  @GetMapping("/")
  public String home(Model model, HttpSession session) {
    BoardDao boardDao = sqlsession.getMapper(BoardDao.class);
    List<Board> boardList = boardDao.getList();
    
    model.addAttribute("boardList", boardList);
    return "home";
  }

  @GetMapping("/reg")
  public String reg() {
    return "reg";
  }

  @PostMapping("/reg")
  public String regOk(Member member) throws ClassNotFoundException, SQLException {
    MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
    memberDao.join(member);

    return "redirect:/";
  }

  @GetMapping("/login")
  public String login() throws ClassNotFoundException, SQLException {
    return "login";
  }

  @PostMapping("/loginOk")
  public ModelAndView loginOk(Member member, HttpServletRequest request) throws ClassNotFoundException, SQLException {
    MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
    ModelAndView mav = new ModelAndView();
    
    if (memberDao.selectMember(member.getId()).getPassword().equals(member.getPassword())) {
      HttpSession session = request.getSession();
      session.setAttribute("user", member);
      mav.setViewName("redirect:/");
    } else {
      mav.setViewName("login");
    }

    return mav;
  }
  
  @GetMapping("/board/{boardId}")
  public String detail(@PathVariable int boardId, Model model, HttpSession session) {
    if (session.getAttribute("user") == null) {
      return "redirect:/";
    }
    BoardDao boardDao = sqlsession.getMapper(BoardDao.class);
    Board board = boardDao.getBoard(boardId);
    CommentDao commentDao = sqlsession.getMapper(CommentDao.class);
    List<Comment> commentList = commentDao.getComments(boardId);

    model.addAttribute("commentList", commentList);
    model.addAttribute("board", board);
    return "board";
  }
  
  @PostMapping("/board/{boardId}")
  public String writeComment(@PathVariable int boardId, Comment comment, HttpSession session) {
    CommentDao commentDao = sqlsession.getMapper(CommentDao.class);
    Member member = (Member) session.getAttribute("user");
    comment.setWriter(member.getId());
    commentDao.writeComment(comment);
    
    return "redirect:/board/" + boardId;
  }
  
  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}
