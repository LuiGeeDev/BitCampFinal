package kr.or.bit.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.GroupMemberDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.dao.ScheduleDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Course;
import kr.or.bit.model.Files;
import kr.or.bit.model.Group;
import kr.or.bit.model.Homework;
import kr.or.bit.model.Member;
import kr.or.bit.model.PageMaker;
import kr.or.bit.model.Project;
import kr.or.bit.model.ProjectMember;
import kr.or.bit.model.Schedule;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.BoardService;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Controller
@RequestMapping("/myclass")
public class MyClassController {
  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private BoardService boardService;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private ArticleInsertService articleInsertService;

  @GetMapping("/project")
  public String projectPage() {
    return "myclass/project/main";
  }

  @GetMapping("/troubleshooting")
  public String troubleshootingPage() {
    return "myclass/troubleshooting/main";
  }

  @GetMapping("/troubleshooting/write")
  public String writeNewIssue() {
    return "myclass/troubleshooting/write";
  }

  @GetMapping("/troubleshooting/read")
  public String readIssue() {
    return "myclass/troubleshooting/detail";
  }

  @GetMapping("/chat")
  public String chatPage() {
    return "myclass/chat/main";
  }

  @GetMapping("/qna")
  public String qnaPage() {
    return "myclass/qna/home";
  }

  @GetMapping("/create/project")
  public String projectPage(Model model) {
    String teacherName = Helper.userName();
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Member teacher = memberDao.selectMemberByUsername(teacherName); // 강사 저장
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    Course course = courseDao.selectCourse(teacher.getCourse_id()); // 코스 저장
    List<Member> memberList = memberDao.selectAllMembersByMycourse(teacher.getCourse_id()); // 코스에 속한 학생리스트 저장
    model.addAttribute("memberList", memberList);
    model.addAttribute("course", course);
    return "myclass/teacher/create/project";
  }

  @PostMapping("/create/project")
  @Transactional
  public String createProject(@RequestBody Project project) {
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    GroupMemberDao groupMemberDao = sqlSession.getMapper(GroupMemberDao.class);
    String teacherName = Helper.userName();
    Member teacher = memberDao.selectMemberByUsername(teacherName); // 강사 저장
    int course_id = teacher.getCourse_id();
    project.setCourse_id(course_id);
    projectDao.insertProject(project);
    System.out.println("프로젝트 생성 성공");
    Project newProject = projectDao.selectRecentProject(course_id);
    List<ProjectMember> leaderList = new ArrayList<>();
    List<ProjectMember> students = project.getStudents();
    for (ProjectMember pm : students) {
      if (pm.isLeader()) {
        leaderList.add(pm);
      }
    }
    for (ProjectMember leader : leaderList) {
      Group group = new Group();
      group.setGroup_no(leader.getGroup());
      group.setLeader_name(leader.getUsername());
      group.setProject_id(newProject.getId());
      groupDao.insertGroup(group);
    }
    for (ProjectMember member : students) {
      int group_no = member.getGroup();
      String username = member.getUsername();
      Group newGroup = groupDao.selectMyNewGroup(newProject.getId(), group_no);
      Member newMember = new Member();
      newMember.setGroup_id(newGroup.getId());
      newMember.setUsername(username);
      groupMemberDao.insertGroupMember(newMember);
    }
    return "redirect:/myclass/teacher/setting";
  }
  
  @GetMapping("/create/board")
  public String boardPage(Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    List<Board> boardlist = boardDao.selectMyClassBoard(user.getCourse_id());
    
    model.addAttribute("boardlist", boardlist);
    return "myclass/teacher/create/board";
  }
  

  @GetMapping("/homework")
  public String homework(@RequestParam(defaultValue = "1") int page, String boardSearch,
      Model model, HttpServletRequest request) {
    String username = Helper.userName();
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Member member = memberDao.selectMemberByUsername(username);
    List<Article> homeworkList = null;
    Pager pager = null;
    if(boardSearch != null) {
      pager = new Pager(page, homeworkDao.countHomeworkArticleBySearchWorkd(member.getCourse_id(), boardSearch));
      homeworkList = homeworkDao.selectHomeworkArticleBySearchWord(pager, member.getCourse_id(), boardSearch); 
      model.addAttribute("boardSearch", boardSearch);
    } else {
      pager = new Pager(page, homeworkDao.countAllHomeworkArticle(member.getCourse_id())); 
      homeworkList = homeworkDao.selectAllHomeworkArticle(pager, member.getCourse_id());
    }

    model.addAttribute("userRole", member.getRole());
    model.addAttribute("pager", pager);
    model.addAttribute("homeworkList", homeworkList);
    model.addAttribute("page", page);
    return "myclass/homework/list";
  }

  @GetMapping("/homework/detail")
  public String homeworkDetailPage(Model model, int id, HttpServletRequest request ) {
    
    Article article = articleService.selectOneArticle("homework", id);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupMemberDao groupMemberDao = sqlSession.getMapper(GroupMemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    List<Article> replies = articleDao.selectHomeworkReplies(id);
    
    for(Article reply : replies) {
      reply.setTimeLocal(reply.getTime().toLocalDateTime());
      Homework homework = homeworkDao.selectHomeworkByArticleId(reply.getId());
      List<Files> files = new ArrayList<Files>();
      files.add(filesDao.selectFilesById(homework.getFile1()));
      files.add(filesDao.selectFilesById(homework.getFile2()));
      homework.setFiles(files);
      Member replyMember = new Member();
      replyMember = memberDao.selectMemberByUsername(reply.getUsername());
      int groupId = groupMemberDao.getGroupIdByUsername(reply.getUsername());
      replyMember.setGroup_no(groupDao.getGroupNoByGroupId(groupId));
      reply.setWriter(replyMember); 
      reply.setOption(homework);
    }
    
    String username = Helper.userName();
    Member member = memberDao.selectMemberByUsername(username);
    model.addAttribute("userRole", member.getRole());
    model.addAttribute("article", article);
    model.addAttribute("replies", replies);
    model.addAttribute("page",request.getParameter("page"));
    model.addAttribute("boardSearch", request.getParameter("boardSearch"));

    return "myclass/homework/detail";
  }

  @PostMapping("/homework/detail")
  public String submitHomeworkDetail(Article article, MultipartFile file1, MultipartFile file2,
      HttpServletRequest request,Model model) {
    
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Member member = memberDao.selectMemberByUsername(Helper.userName());
    Board board = boardDao.selectBoardByCourseId(member.getCourse_id(), 4);
    article.setUsername(Helper.userName());
    article.setBoard_id(board.getId());
    article.setTitle("과제제출");
    article.setContent("과제제출");
    article.setLevel(2);
    Homework homework = new Homework();
    List<MultipartFile> files = new ArrayList<>();
    files.add(file1);
    files.add(file2);
    articleInsertService.writeReplyArticle(article, homework, files, request);
    model.addAttribute("boardSearch", request.getParameter("boardSearch"));
    return "redirect:/myclass/homework/detail?id=" + article.getId() +"&page="+request.getParameter("page");
  }

  @GetMapping("/homework/write")
  public String homeworkWritePage() {
    return "myclass/homework/write";
  }

  @PostMapping("/homework/write")
  @Transactional
  public String writeHomeworkArticle(Article article, Date end_date, HttpServletRequest request) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    
    
    Member member = memberDao.selectMemberByUsername(Helper.userName());
    int board_id = boardDao.selectBoardByCourseId(member.getCourse_id(), 4).getId();
    article.setUsername(Helper.userName());
    article.setBoard_id(board_id);
    Homework homework = new Homework();
    homework.setEnd_date(end_date);
    articleInsertService.writeArticle(article, homework, null, request);
    
    Schedule schedule = new Schedule();

    schedule.setCourse_id(member.getCourse_id());
    schedule.setContent(article.getTitle());
    schedule.setEnd_date(homework.getEnd_date());
    schedule.setColor("green");
    schedule.setGroup_id(0);
    
    scheduleDao.insertSchedule(schedule);
    
    return "redirect:/myclass/homework";
  }

  @GetMapping("")
  public String mainPage(Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    
    Course course = courseDao.selectCourse(memberDao.selectMemberByUsername(Helper.userName()).getCourse_id());
    course.setEndDate(course.getEnd_date().toLocalDate());
    course.setStartDate(course.getStart_date().toLocalDate());
    Period diff = Period.between(course.getStartDate(), course.getEndDate());
    Period diff2 = Period.between(course.getStartDate(), LocalDate.now());
    int completion = Math.round((float) diff2.getDays() / diff.getDays() * 100);
    List<Article> recentArticles = articleDao.selectArticlesForClassMain(course.getId());
    for (Article article : recentArticles) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }

    model.addAttribute("course", course);
    model.addAttribute("recentArticles", recentArticles);
    model.addAttribute("completion", completion);
    return "myclass/home";
  }

  @GetMapping("/homework/edit")
  public String editHomeworkArticle(Model model, int id, HttpServletRequest request) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Article article = articleDao.selectOneArticle(id);
    Homework homework = homeworkDao.selectHomeworkByArticleId(id);
    article.setOption(homework);
    
    model.addAttribute("article", article);
    model.addAttribute("boardSearch", request.getParameter("boardSearch"));
    return "myclass/homework/edit";
  }

  @PostMapping("/homework/edit")
  public String editOkHomeworkArticle(Article updateArticle, HttpServletRequest request, String boardSearch) throws UnsupportedEncodingException {
    request.setCharacterEncoding("UTF-8");
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Homework homework = homeworkDao.selectHomeworkByArticleId(updateArticle.getId());
    updateArticle.setOption(homework);
    articleDao.updateArticle(updateArticle);
    homeworkDao.updateHomeworkArticle(updateArticle);
    System.out.println("///////////////////////"+boardSearch);
    return "redirect:/myclass/homework/detail?id=" + updateArticle.getId() + "&page="+request.getParameter("page") +"&boardSearch="+boardSearch;
  }

  @PostMapping("/homework/delete")
  public String deleteHomeworkArticle(Article article) {
    System.out.println(article);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(article.getId());
    return "redirect:/myclass/homework";
  }
  
  @GetMapping("/setting")
  public String manageMain() {
    return "myclass/teacher/managing";
  }
}