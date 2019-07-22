package kr.or.bit.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CategoryDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.model.Board;
import kr.or.bit.model.Category;
import kr.or.bit.model.Group;
import kr.or.bit.model.Member;
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
    List<Group> projects = projectDao.selectMyProject(username);

    // ---------------게시판 시작----------------------

    CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
    List<Category> categories = categoryDao.selectCategoryByCourseid(user.getCourse_id());

    HashMap<String, List<Board>> boardMap = new HashMap<>();
    System.out.println(user.getCourse_id());
    
    for (Category category : categories) {
      List<Board> boardlist = boardDao.selectBoardByCategory(category.getId(), user.getCourse_id());
      boardMap.put(category.getCategory(), boardlist);
    }
    System.out.println(boardMap);
    // --------------게시판 끝---------------------------
    request.setAttribute("boardMap", boardMap);
    request.setAttribute("boardList", board);
    request.setAttribute("projects", projects);
  }
}
