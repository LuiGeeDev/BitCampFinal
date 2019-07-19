package kr.or.bit.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.transform.impl.AddDelegateTransformer;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javassist.expr.NewArray;
import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CategoryDao;
import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GroupDao;
import kr.or.bit.dao.GroupMemberDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.ProjectDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.ScheduleDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.dao.TimelineDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.BoardAddRemove;
import kr.or.bit.model.Category;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Course;
import kr.or.bit.model.Files;
import kr.or.bit.model.Group;
import kr.or.bit.model.Homework;
import kr.or.bit.model.Member;
import kr.or.bit.model.Project;
import kr.or.bit.model.ProjectMember;
import kr.or.bit.model.Qna;
import kr.or.bit.model.Schedule;
import kr.or.bit.model.Tag;
import kr.or.bit.model.Timeline;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.ArticleUpdateService;
import kr.or.bit.service.ArticleVoteService;
import kr.or.bit.service.BoardService;
import kr.or.bit.service.CommentService;
import kr.or.bit.service.MypageService;
import kr.or.bit.service.TagService;
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
  @Autowired
  private ArticleUpdateService articleUpdateService;
  @Autowired
  private TagService tagService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ArticleVoteService articleVoteService;
  @Autowired
  private MypageService mypageService;

  @GetMapping("")
  public String mainPage(Model model) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    ProjectDao projectDao = sqlSession.getMapper(ProjectDao.class);
    
    Course course = courseDao.selectCourse(memberDao.selectMemberByUsername(Helper.userName()).getCourse_id());
    course.setEndDate(course.getEnd_date().toLocalDate());
    course.setStartDate(course.getStart_date().toLocalDate());
    List<Article> recentHomework = homeworkDao.selectRecentHomeworkArticle(course.getId());
    Period diff = Period.between(course.getStartDate(), course.getEndDate());
    Period diff2 = Period.between(course.getStartDate(), LocalDate.now());
    for (Article article : recentHomework) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setOption(homeworkDao.selectHomeworkByArticleId(article.getId()));
    }
    int completion = Math.round((float) diff2.getDays() / diff.getDays() * 100);
    List<Article> recentArticles = articleDao.selectArticlesForClassMain(course.getId());
    for (Article article : recentArticles) {
      article.setBoardtype(boardDao.selectBoardById(article.getBoard_id()).getBoardtype());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }
    model.addAttribute("recentHomework", recentHomework);
    model.addAttribute("course", course);
    model.addAttribute("recentArticles", recentArticles);
    model.addAttribute("completion", completion);
    model.addAttribute("project", projectDao.selectRecentProjectByCourseId(memberDao.selectMemberByUsername(Helper.userName()).getCourse_id()));
    model.addAttribute("group_id", memberDao.selectMemberByUsername(Helper.userName()).getGroup_id());
    
    return "myclass/home";
  }

  @GetMapping("/qna")
  public String qnaPage(@RequestParam(defaultValue = "1") int page, String boardSearch, String criteria, Model model)
      throws Exception {
    QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member member = memberDao.selectMemberByUsername(username);
    int course_id = member.getCourse_id();
    List<Article> qnaList = null;
    Pager pager = null;
    if (boardSearch != null) {
      if (criteria.equals("titleOrContent")) {
        pager = new Pager(page, qnaDao.countQnaArticleByTitleOrContent(boardSearch, course_id));
      } else if (criteria.equals("title")) {
        pager = new Pager(page, qnaDao.countQnaArticleByTitle(boardSearch, course_id));
      } else {
        pager = new Pager(page, qnaDao.countQnaArticleByWriter(boardSearch, course_id));
      }
      qnaList = articleService.selectQnaArticlesByboardSearch(pager, boardSearch, criteria, course_id);
      model.addAttribute("boardSearch", boardSearch);
    } else {
      pager = new Pager(page, qnaDao.countAllQnaArticle(course_id));
      qnaList = articleService.selectAllQnaArticles(pager, course_id);
    }
    model.addAttribute("qnaList", qnaList);
    model.addAttribute("pager", pager);
    model.addAttribute("page", page);
    model.addAttribute("criteria",criteria);
    return "myclass/qna/home";
  }

  @GetMapping("/qna/write")
  public String writeQna(Model model) {
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    List<Tag> tags = stackdao.showTagList();
    model.addAttribute("tags", tags);
    return "myclass/qna/write";
  }

  @PostMapping("/qna/write")
  public String writeOkQna(Article article, String tag) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<String> tagList = new ArrayList<>();
    String[] splitStr = tag.split("#");
    for (int i = 1; i < splitStr.length; i++) {
      tagList.add(splitStr[i].trim());
    }
    List<Tag> tags = tagService.selectTagByName(tagList);
    article.setTags(tags);
    article.setUsername(Helper.userName());
    article.setBoard_id(boardDao.selectBoardByCourseId(memberDao.selectMemberByUsername(Helper.userName()).getCourse_id(), 5).getId());
    articleInsertService.writeQnaArticle(article, tagList);
    return "redirect:/myclass/qna";
  }

  @GetMapping("/qna/content")
  public String GetQnaContent(int id, Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    Article article = articleService.selectOneArticle("qna", id);
    int scrapCount = mypageService.scrapCount(id, Helper.userName());
    int voteCount = articleVoteService.selectVote(id, Helper.userName());
    Qna qna = (Qna) article.getOption();
    int adopted = qna.getAdopted_answer();
    for (Comment c : article.getCommentlist()) {
      c.setWriter(memberDao.selectMemberByUsername(c.getUsername()));
    }
    if (adopted != 0) {
      Comment comment = stackdao.selectAdoptedAnswer(adopted);
      comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      model.addAttribute("adoptedanswer", comment);
    }
   
    
    
    model.addAttribute("scrapCount",scrapCount);
    model.addAttribute("voteCount", voteCount);
    model.addAttribute("qnacontent", article);
    model.addAttribute("voteStatus", articleVoteService.selectVote(article.getId(), Helper.userName()));
    
    articleUpdateService.viewCount(article);
    return "myclass/qna/content";
  }

  @GetMapping("/qna/edit")
  public String editQna(int id, Model model) {
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    List<Tag> tags = stackdao.selectTagList(id);
    Article article = articleService.selectOneArticle("qna", id);
    model.addAttribute("tags", tags);
    model.addAttribute("article", article);
    return "myclass/qna/edit";
  }

  @PostMapping("/qna/edit")
  public String editQnaArticle(Article article) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    article.setUsername(Helper.userName());
    article.setBoard_id(boardDao.selectBoardByCourseId(memberDao.selectMemberByUsername(Helper.userName()).getCourse_id(), 5).getId());
    articleUpdateService.updateArticle(article);
    return "redirect:/myclass/qna";
  }

  @GetMapping("/qna/delete")
  public String deleteQna(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(id);
    return "redirect:/myclass/qna";
  }

  @PostMapping("/qna/commentwrite")
  public @ResponseBody List<Comment> QnaCommentWrite(int article_id, Comment comment) {
    comment.setUsername(Helper.userName());
    boardService.writeComment(article_id, comment);
    List<Comment> commentList = boardService.getCommentList(article_id);
    return commentList;
  }

  @GetMapping("/qna/commentdelete")
  public String QnaCommentDelete(int id) {
    Comment comment = commentService.selectOnecomment(id);
    int article_id = comment.getArticle_id();
    commentService.deleteComment(id);
    return "redirect:/myclass/qna/content?id=" + article_id;
  }

  @GetMapping("/qna/plusvote")
  public String QnaplustVote(int id) {
    articleVoteService.insertVote(id, Helper.userName());
    return "redirect:/myclass/qna/content?id=" + id;
  }

  @GetMapping("/qna/chooseanswer")
  public String QnaChooseAnswer(int comment_id, int article_id) {
    QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
    qnaDao.chooseAnswer(comment_id, article_id);
    return "redirect:/myclass/qna/content?id=" + article_id;
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
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    TimelineDao timelineDao = sqlSession.getMapper(TimelineDao.class);
    String teacherName = Helper.userName();
    Member teacher = memberDao.selectMemberByUsername(teacherName); // 강사 저장
    int course_id = teacher.getCourse_id();
    project.setCourse_id(course_id);
    projectDao.insertProject(project);
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
    for (int i = 1; i <= leaderList.size(); i++) {
      Board board = new Board();
      board.setBoard_name("트러블슈팅" + newProject.getSeason() + i);
      board.setBoardtype(6);
      board.setCourse_id(course_id);
      boardDao.insertBoard(board);
    }
    Schedule schedule = new Schedule();
    schedule.setStart(newProject.getStart_date());
    schedule.setEnd(newProject.getEnd_date());
    schedule.setTitle(newProject.getProject_name());
    schedule.setColor("tomato");
    schedule.setGroup_id(0);
    schedule.setCourse_id(teacher.getCourse_id());
    scheduleDao.insertSchedule(schedule);
    for (int i = 0; i < leaderList.size(); i++) {
      Timeline timeline = new Timeline();
      timeline.setTitle("프로젝트 시작");
      timeline.setContent(newProject.getProject_name());
      timeline.setGroup_id(memberDao.selectMemberByUsername(leaderList.get(i).getUsername()).getGroup_id());
      timeline.setUsername(Helper.userName());
      timelineDao.insertTimeline(timeline);
    }
    return "redirect:/myclass/setting";
  }

  @GetMapping("/create/board")
  public String boardPage(Model model) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    CategoryDao categorydao = sqlSession.getMapper(CategoryDao.class);
    
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    List<Board> boardlist = boardDao.selectMyClassBoard(user.getCourse_id());
    
    List<Category> categories = categorydao.selectCategoryByCourseid(user.getCourse_id());
    
    model.addAttribute("boardlist", boardlist);
    model.addAttribute("categories", categories);
    return "myclass/teacher/create/board";
  }

  @PostMapping("/create/board")
  public void createBoard(@RequestBody BoardAddRemove boardAddRemove) {
    boardService.decideBoardAddOrRemove(boardAddRemove);
  }

  @GetMapping("/homework")
  public String homework(@RequestParam(defaultValue = "1") int page, String boardSearch, Model model,
      HttpServletRequest request) {
    String username = Helper.userName();
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Member member = memberDao.selectMemberByUsername(username);
    List<Article> homeworkList = null;
    Pager pager = null;
    if (boardSearch != null) {
      pager = new Pager(page, homeworkDao.countHomeworkArticleBySearchWorkd(member.getCourse_id(), boardSearch));
      homeworkList = homeworkDao.selectHomeworkArticleBySearchWord(pager, member.getCourse_id(), boardSearch);
      model.addAttribute("boardSearch", boardSearch);
    } else {
      pager = new Pager(page, homeworkDao.countAllHomeworkArticle(member.getCourse_id()));
      homeworkList = homeworkDao.selectAllHomeworkArticle(pager, member.getCourse_id());
    }
    for (Article article : homeworkList) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    }
    model.addAttribute("userRole", member.getRole());
    model.addAttribute("pager", pager);
    model.addAttribute("homeworkList", homeworkList);
    model.addAttribute("page", page);
    return "myclass/homework/list";
  }

  @GetMapping("/homework/detail")
  public String homeworkDetailPage(Model model, int id, HttpServletRequest request) {
    Article article = articleService.selectOneArticle("homework", id);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GroupMemberDao groupMemberDao = sqlSession.getMapper(GroupMemberDao.class);
    GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleUpdateService.viewCount(article);
    List<Article> replies = articleDao.selectHomeworkReplies(id);
    for (Article reply : replies) {
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
    model.addAttribute("username", username);
    model.addAttribute("userRole", member.getRole());
    model.addAttribute("article", article);
    model.addAttribute("replies", replies);
    model.addAttribute("page", request.getParameter("page"));
    model.addAttribute("boardSearch", request.getParameter("boardSearch"));
    return "myclass/homework/detail";
  }

  @PostMapping("/homework/detail")
  public String submitHomeworkDetail(@RequestParam("original_id") int original_id, @RequestParam("end_date") Date end_date, @RequestParam("page") int page,
      @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, HttpServletRequest request, Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Member member = memberDao.selectMemberByUsername(Helper.userName());
    Board board = boardDao.selectBoardByCourseId(member.getCourse_id(), 4);
    Homework homework = new Homework();
    homework.setEnd_date(end_date);
    Article article = new Article();
    article.setOriginal_id(original_id);
    article.setUsername(Helper.userName());
    article.setBoard_id(board.getId());
    article.setTitle("과제제출");
    article.setContent("과제제출");
    article.setLevel(2);
    List<MultipartFile> files = new ArrayList<>();
    files.add(file1);
    files.add(file2);
    articleInsertService.writeReplyArticle(article, homework, files, request);
    model.addAttribute("boardSearch", request.getParameter("boardSearch"));
    return "redirect:/myclass/homework/detail?id=" + article.getOriginal_id() + "&page=" + page;
  }

  @GetMapping("/homework/write")
  public String homeworkWritePage() {
    return "myclass/homework/write";
  }

  @PostMapping("/homework/write")
  @Transactional
  public String writeHomeworkArticle(Article article, Date end_date, HttpServletRequest request) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
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
    article = articleDao.selectOneArticle(article.getId());
    schedule.setArticle_id(article.getId());
    schedule.setCourse_id(member.getCourse_id());
    schedule.setTitle(article.getTitle());
    schedule.setStart(Date.valueOf(article.getTime().toLocalDateTime().toLocalDate()));
    schedule.setEnd(homework.getEnd_date());
    schedule.setColor("green");
    schedule.setGroup_id(0);
    scheduleDao.insertScheduleByBoard(schedule);
    return "redirect:/myclass/homework";
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
  public String editOkHomeworkArticle(Article updateArticle, HttpServletRequest request, String boardSearch)
      throws UnsupportedEncodingException {
    request.setCharacterEncoding("UTF-8");
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    Homework homework = homeworkDao.selectHomeworkByArticleId(updateArticle.getId());
    updateArticle.setOption(homework);
    articleDao.updateArticle(updateArticle);
    homeworkDao.updateHomeworkArticle(updateArticle);
    return "redirect:/myclass/homework/detail?id=" + updateArticle.getId() + "&page=" + request.getParameter("page")
        + "&boardSearch=" + boardSearch;
  }

  @PostMapping("/homework/delete")
  public String deleteHomeworkArticle(Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
    article = articleDao.selectOneArticle(article.getId());
    articleDao.deleteArticle(article.getId());
    scheduleDao.deleteSchedule(scheduleDao.selectScheduleByArticleId(article.getId()).getId());
    return "redirect:/myclass/homework";
  }

  @GetMapping("/setting")
  public String manageMain(Model model) {
    CourseDao coursedao = sqlSession.getMapper(CourseDao.class);
    MemberDao memberdao = sqlSession.getMapper(MemberDao.class);
    ProjectDao projectdao = sqlSession.getMapper(ProjectDao.class);
    GroupDao groupdao = sqlSession.getMapper(GroupDao.class);
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    HomeworkDao homeworkdao = sqlSession.getMapper(HomeworkDao.class);
    
    String username = Helper.userName();
    Member member = memberdao.selectMemberByUsername(username);
    Course course = coursedao.selectCourse(member.getCourse_id());
    Project project = projectdao.selectRecentProjectByCourseId(member.getCourse_id());
    
    List<Group> groups = groupdao.selectAllGroupByProject(project.getId());
    
    project.setStartDateLocal(project.getStart_date().toLocalDate());
    project.setEndDateLocal(project.getEnd_date().toLocalDate());
    course.setStartDateLocal(course.getStart_date().toLocalDate());
    course.setEndDateLocal(course.getEnd_date().toLocalDate());
    Period ccDay = Period.between(course.getStartDateLocal(), course.getEndDateLocal());
    Period cDay = Period.between(course.getEndDateLocal(), LocalDate.now());
    
    Period ddDay = Period.between(project.getStartDateLocal(), project.getEndDateLocal());
    Period dDay = Period.between(project.getStartDateLocal(), LocalDate.now());
    
    Article homeworkarticle = articledao.selectRecentHomework(username);
    Homework homework = homeworkdao.selectHomeworkByArticleId(homeworkarticle.getId());
    homeworkarticle.setOption(homework);
    
    List<Article> recentStackArticle = articledao.selectRecentStackbyCourse(course.getId());
    
    List<Article> homeworkarticlere = articledao.selectHomeworkReplies(homeworkarticle.getId());

    for(Article a : recentStackArticle) {
      a.setWriter(memberdao.selectMemberByUsername(a.getUsername()));
      a.setTimeLocal(a.getTime().toLocalDateTime());
    }
    
    model.addAttribute("course", course);
    model.addAttribute("project", project);
    model.addAttribute("project_percent", (int)((float)(dDay.getDays() / (float)(ddDay.getDays()))*100));
    model.addAttribute("enable", memberdao.getCountCourseMember(course.getId(), "enable"));
    model.addAttribute("disable", memberdao.getCountCourseMember(course.getId(), "disable"));
    model.addAttribute("all", memberdao.getCountCourseMember(course.getId(), "enable") + memberdao.getCountCourseMember(course.getId(), "disable"));
    model.addAttribute("course_percent", (int)((float)(cDay.getDays() / (float)(ccDay.getDays()))*100));
    model.addAttribute("groups", groups);
    model.addAttribute("homeworkarticle",homeworkarticle);
    model.addAttribute("homeworkarticlere",homeworkarticlere);
    model.addAttribute("stackarticle",recentStackArticle);
    
    return "myclass/teacher/managing";
  }
}