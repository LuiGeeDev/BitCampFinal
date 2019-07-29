package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.ChecklistDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.dao.TimelineDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Checklist;
import kr.or.bit.model.Group;
import kr.or.bit.model.Project;
import kr.or.bit.model.Timeline;

/*
 * 내 클래스 -> 내 프로젝트에 관련된 메서드를 포함한 컨트롤러 
 * */

@Controller
@RequestMapping("myclass/project")
public class ProjectController {
  @Autowired
  private SqlSession sqlSession;

  @GetMapping("")
  public String projectPage(int group_id, Model model) {
    if (group_id == 0) {
      return "redirect:/myclass/setting";
    }
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    ChecklistDao checklistDao = sqlSession.getMapper(ChecklistDao.class);
    TimelineDao timelineDao = sqlSession.getMapper(TimelineDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    StackDao stackDao = sqlSession.getMapper(StackDao.class);

    List<Timeline> timelineList = timelineDao.selectTimelineByGroupId(group_id);
    Group group = groupDao.selectGroupById(group_id);
    Project project = projectDao.selectProject(group.getProject_id());
    Board tsBoard = boardDao.selectTroubleShootingBoard(project.getCourse_id(), project.getSeason(), group.getId());
    List<Article> troubleShootingList = articleDao.selectAllArticleByBoardId(tsBoard.getId());
    for (Article article : troubleShootingList) {
      article.setOption(troubleShootingDao.selectTroubleShootingByArticleId(article.getId()));
      article.setTags(stackDao.selectTagList(article.getId()));
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    }
    List<Checklist> checklist = checklistDao.selectAllChecklist(group_id);
    List<String> checklistContents = new ArrayList<String>();
    for (Checklist todo : checklist) {
      checklistContents.add(todo.getContent());
    }
    for (Timeline timeline : timelineList) {
      timeline.setWriter(memberDao.selectMemberByUsername(timeline.getUsername()));
    }
    model.addAttribute("group", group);
    model.addAttribute("checklist", checklist);
    model.addAttribute("timelineList", timelineList);
    model.addAttribute("troubleShootingList", troubleShootingList);
    return "myclass/project/main";
  }

  @GetMapping("/chat")
  public String chatPage(int group_id, Model model) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    Group group = groupDao.selectGroupById(group_id);
    model.addAttribute("group", group);
    return "myclass/chat/main";
  }

  @PostMapping("/link")
  public String linkUpdate(Group group) {
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    groupDao.updateGroup(group);
    return "redirect:/myclass/project?group_id=" + group.getId();
  }
}
