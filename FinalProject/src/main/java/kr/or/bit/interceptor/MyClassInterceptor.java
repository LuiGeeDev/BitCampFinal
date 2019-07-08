package kr.or.bit.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.model.Board;
import kr.or.bit.model.Group;
import kr.or.bit.model.Member;
import kr.or.bit.model.Project;
import kr.or.bit.utils.Helper;

public class MyClassInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private SqlSession sqlSession;
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    List<Board> board = boardDao.selectMyClassBoard(user.getCourse_id());
    List<Project> projects = projectDao.selectMyProject(username);
    
    request.setAttribute("boardList", board);
    request.setAttribute("projects", projects);
  }
}
