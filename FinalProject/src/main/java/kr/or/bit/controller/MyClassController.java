package kr.or.bit.controller;

import java.sql.Date;
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
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Course;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;
import kr.or.bit.model.Group;
import kr.or.bit.model.Homework;
import kr.or.bit.model.Member;
import kr.or.bit.model.Project;
import kr.or.bit.model.ProjectMember;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.BoardService;
import kr.or.bit.utils.Helper;

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

  @GetMapping("/create")
  public String projectPage(Model model) {
    
    String teacherName = Helper.userName();
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Member teacher = memberDao.selectMemberByUsername(teacherName); //강사 저장
    
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    Course course = courseDao.selectCourse(teacher.getCourse_id()); //코스 저장
    List<Member> memberList = memberDao.selectAllMembersByMycourse(teacher.getCourse_id()); //코스에 속한 학생리스트 저장
    
    model.addAttribute("memberList",memberList);
    model.addAttribute("course", course);
    return "myclass/create/main";
  }
  
  @PostMapping("/create")
  @Transactional
  public String createProject(@RequestBody Project project) {
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    GroupMemberDao groupMemberDao = sqlSession.getMapper(GroupMemberDao.class);
    
    String teacherName = Helper.userName();
    
    Member teacher = memberDao.selectMemberByUsername(teacherName); //강사 저장
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
    for(ProjectMember member : students) {
      int group_no = member.getGroup();
      String username = member.getUsername();
      Group newGroup = groupDao.selectMyNewGroup(newProject.getId(), group_no);
      Member newMember = new Member();
      
      newMember.setGroup_id(newGroup.getId());
      newMember.setUsername(username);
      
      groupMemberDao.insertGroupMember(newMember);
    }
    
    
    
    return "redirect:/myclass/create";
  }
  
  
  @GetMapping("/homework")
  public String homework(Model model) {
//    List<Article> homeworkList = articleService.selectAllArticle("homework", HOMEWORK_BOARD_ID);
//    model.addAttribute("homeworkList", homeworkList);
    String username = Helper.userName();
    
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Member member = memberDao.selectMemberByUsername(username);
    model.addAttribute("homeworkList", homeworkDao.selectAllHomeworkArticle(member.getCourse_id()));
    
    return "myclass/homework/list";
  }
  
  @GetMapping("/homework/detail")
  public String homeworkDetailPage(Model model, int id) { 
    Article article = articleService.selectOneArticle("homework", id);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
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
      
      reply.setOption(homework);
      
      reply.setWriter(memberDao.selectMemberByUsername(reply.getUsername()));
      
    }
    
    
    model.addAttribute("article", article);
    model.addAttribute("replies", replies);
    return "myclass/homework/detail";
  }
  
  @PostMapping("/homework/detail")
  public String submitHomeworkDetail(Article article, MultipartFile file1, MultipartFile file2, HttpServletRequest request) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
   
    Member member = memberDao.selectMemberByUsername(Helper.userName());
    Board board = boardDao.selectBoardByCourseId(member.getCourse_id(), 4);
    
    article.setUsername(Helper.userName());
    
    article.setBoard_id(board.getId());
    article.setTitle("과제제출");
    article.setLevel(2);
    Homework homework = new Homework();
    List<MultipartFile> files = new ArrayList<>();
    files.add(file1);
    files.add(file2);
    articleInsertService.writeReplyArticle(article, homework, files, request);
    return "redirect:/myclass/homework/detail?id="+article.getId();
  }
  
  @GetMapping("/homework/write")
  public String homeworkWritePage() {
    return "myclass/homework/write";
  }
  
  @PostMapping("/homework/write")
  public String writeHomeworkArticle(Article article, Date end_date,HttpServletRequest request) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Member member = memberDao.selectMemberByUsername(Helper.userName());
    int board_id = boardDao.selectBoardByCourseId(member.getCourse_id(), 4).getId();
    article.setUsername(Helper.userName());
    article.setBoard_id(board_id);
    Homework homework = new Homework();
    homework.setEnd_date(end_date);
    articleInsertService.writeArticle(article, homework, null, request);
    return "redirect:/myclass/homework";
  }
  
  @GetMapping("/homework/edit")
  public String editHomeworkArticle(Model model, int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Article article = articleDao.selectOneArticle(id);
    Homework homework = homeworkDao.selectHomeworkByArticleId(id);
    article.setOption(homework);
    model.addAttribute("article", article);
    
    return "myclass/homework/edit";
  }
  
  @PostMapping("/homework/edit")
  public String editOkHomeworkArticle(Article updateArticle) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Homework homework = homeworkDao.selectHomeworkByArticleId(updateArticle.getId());
    updateArticle.setOption(homework);
    
    articleDao.updateArticle(updateArticle);
    homeworkDao.updateHomeworkArticle(updateArticle);
    
    return "redirect:/myclass/homework/detail?id="+updateArticle.getId();
  }
  
  @PostMapping("/homework/delete")
  public String deleteHomeworkArticle(Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(article.getId());
    return "redirect:/myclass/homework";
  }
  @GetMapping("/main/home")
  public String mainPage() {
    return "myclass/main/home";
  }
}