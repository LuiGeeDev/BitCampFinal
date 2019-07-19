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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.ScrapDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Comment;
import kr.or.bit.model.General;
import kr.or.bit.model.Tag;
import kr.or.bit.model.Member;
import kr.or.bit.model.Qna;
import kr.or.bit.model.Scrap;
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
@RequestMapping("/stack")
public class StackController {
  private final int STACK_BOARD_ID = 1;
  @Autowired
  private SqlSession sqlsession;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ArticleInsertService articleInsertService;
  @Autowired
  private ArticleVoteService articleVoteService;
  @Autowired
  private ArticleUpdateService articleUpdateService;
  @Autowired
  private TagService tagService;
  @Autowired
  private BoardService boardService;
  @Autowired
  private MypageService mypageService;

  @GetMapping("")
  public String listPage(@RequestParam(defaultValue = "1") int page, String boardSearch, String criteria, Model model)
      throws Exception {
    StackDao stackDao = sqlsession.getMapper(StackDao.class);
    MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member member = memberDao.selectMemberByUsername(username);
    List<Article> stackList = null;
    Pager pager = null;
    if (boardSearch != null) {
      if (criteria.equals("titleOrContent")) {
        pager = new Pager(page, stackDao.countStackArticleByTitleOrContent(boardSearch));
      } else if (criteria.equals("title")) {
        pager = new Pager(page, stackDao.countStackArticleByTitle(boardSearch));
      } else {
        pager = new Pager(page, stackDao.countStackArticleByWriter(boardSearch));
      }
      stackList = articleService.selectStackArticlesByboardSearch(pager, boardSearch, criteria);
      
      
      model.addAttribute("boardSearch", boardSearch);
    } else {
      pager = new Pager(page, stackDao.countAllStackArticle());
      stackList = articleService.selectAllStackArticles(pager);
    }
    List<Tag> tags = stackDao.showTagList();
    
    System.out.println(tags);
    
    model.addAttribute("tags",tags);
    model.addAttribute("stacklist", stackList);
    model.addAttribute("pager", pager);
    model.addAttribute("page", page);
    model.addAttribute("criteria",criteria);
    return "stack/home";
  }
  /*
   * //stack 메인으로 이동
   * 
   * @GetMapping("/home") public String getStackList(Model model) { List<Article>
   * article = articleService.selectAllArticle("qna",STACK_BOARD_ID);
   * model.addAttribute("stacklist",article);
   * 
   * return "stack/home"; }
   */

  // stack 게시물 상세보기 버튼
  @GetMapping("/content")
  public String GetStackContent(int id, Model model) {
    MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
    StackDao stackdao = sqlsession.getMapper(StackDao.class);    
    int scrapCount = mypageService.scrapCount(id, Helper.userName());
    int voteCount = articleVoteService.selectVote(id, Helper.userName());
    Article article = articleService.selectOneArticle("qna", id);
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
   /* Scrap scrap = scrapDao.selectOneScrapById(id);*/
    /*model.addAttribute("scrap",scrap);*/
    model.addAttribute("scrapCount",scrapCount);
    model.addAttribute("voteCount",voteCount);
    model.addAttribute("stackcontent", article);
    articleUpdateService.viewCount(article);
    return "stack/content";
  }
  
  @PostMapping("/plusTag")
  public @ResponseBody List<Tag> plusTag(String tag, String color) {
    StackDao stackDao = sqlsession.getMapper(StackDao.class);
    stackDao.plusTag(tag, color);
    List<Tag> taglist = stackDao.showTagList();
    
    return taglist;
  }
  
  @GetMapping("/deleteTag")
  public String deleteTag(String tag) {
    StackDao stackDao = sqlsession.getMapper(StackDao.class);
    stackDao.deleteTagName(tag);
    
    return "redirect:/stack";
  }
  
  @GetMapping("/insertScrap")
  public String insertScrap(int article_id) {
    ScrapDao scrapDao = sqlsession.getMapper(ScrapDao.class);
    Scrap scrap = scrapDao.selectOneScrap(article_id, Helper.userName());
    if(scrap == null) {
      scrapDao.insertScrap(article_id, Helper.userName());
    } else {
      scrapDao.deleteScrap(article_id, Helper.userName());
    }
 
    return "redirect:/stack/content?id=" + article_id;
  }

  // 글쓰기 폼 화면으로..
  @GetMapping("/write")
  public String writeStack(Model model) {
    StackDao stackdao = sqlsession.getMapper(StackDao.class);
    List<Tag> tags = stackdao.showTagList();
    model.addAttribute("tags", tags);
    return "stack/write";
  }

  // 글쓰기 버튼 누르기..
  @PostMapping("/write")
  public String writeOkStack(Article article, String tag) {
    List<String> tagList = new ArrayList<>();
    String[] splitStr = tag.split("#");
    for (int i = 1; i < splitStr.length; i++) {
      tagList.add(splitStr[i].trim());
    }
    List<Tag> tags = tagService.selectTagByName(tagList);
    article.setTags(tags);
    article.setUsername(Helper.userName());
    article.setBoard_id(STACK_BOARD_ID);
    articleInsertService.writeStackArticle(article, tagList);
    return "redirect:/stack";
  }

  @GetMapping("/edit")
  public String editStack(int id, Model model) {
    StackDao stackdao = sqlsession.getMapper(StackDao.class);
    List<Tag> tags = stackdao.selectTagList(id);
    Article article = articleService.selectOneArticle("qna", id);
    model.addAttribute("tags", tags);
    model.addAttribute("article", article);
    return "stack/edit";
  }

  @PostMapping("/edit")
  public String editStackArticle(Article article) {
    article.setUsername(Helper.userName());
    article.setBoard_id(STACK_BOARD_ID);
    General general = new General();
    articleUpdateService.updateArticle(article);
    articleUpdateService.updateArticleOption(article.getId(), general);
    return "redirect:/stack";
  }

  @GetMapping("/delete")
  public String deleteStack(int id) {
    ArticleDao articleDao = sqlsession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(id);
    return "redirect:/stack";
  }

  @PostMapping("/commentwrite")
  public @ResponseBody List<Comment> stackCommentWrite(int article_id, Comment comment) {
    comment.setUsername(Helper.userName());
    boardService.writeComment(article_id, comment);
    List<Comment> commentList = boardService.getCommentList(article_id);
    return commentList;
  }

  @GetMapping("/commentdelete")
  public String stackCommentDelete(int id) {
    Comment comment = commentService.selectOnecomment(id);
    int article_id = comment.getArticle_id();
    commentService.deleteComment(id);
    return "redirect:/stack/content?id=" + article_id;
  }

  @GetMapping("/plusvote")
  public String stackplustVote(int id) {
    articleVoteService.insertVote(id, Helper.userName());
    return "redirect:/stack/content?id=" + id;
  }

  @GetMapping("/chooseanswer")
  public String stackChooseAnswer(int comment_id, int article_id) {
    QnaDao qnaDao = sqlsession.getMapper(QnaDao.class);
    qnaDao.chooseAnswer(comment_id, article_id);
    return "redirect:/stack/content?id=" + article_id;
  }
}
