package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/myclass/board")
public class BoardController {
  private final int GENERAL_BOARD = 3;

  @Autowired
  private SqlSession sqlSession;

  @GetMapping("/")
  public String listPage(int board_id, @RequestParam(defaultValue = "1") int page,
      @RequestParam(required = false) String sort, @RequestParam(required = false) String search, Model model) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Board board = boardDao.selectBoardById(board_id);

    List<Article> articles = null;
    if (sort == null && search == null) {
      articles = articleDao.selectArticlesByPage(board_id, (page - 1) * 20 + 1, page * 20);
    } else if (sort != null) {

    } else if (search != null) {

    }
  
    model.addAttribute("board", board);
    model.addAttribute("articles", articles);

    return null;
  }

  @GetMapping("/write")
  public String writePage(int board_id, Model model) {
    model.addAttribute("board_id", board_id);
    return "myclass/general/write";
  }

  @PostMapping("/write")
  public String writeArticle(Article article, List<MultipartFile> files) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);

    /*
    Article INSERT
    파일 INSERT
    new General(),
    General에 file ID 삽입
    General INSERT
    Article SELECT
    */

    return "redirect:/myclass/board/read?article_id=" + article.getId();
  }

  @GetMapping("/read")
  public String readArticle(int article_id, Model model) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);

    Article article = articleDao.selectOneArticle(article_id);
    General general = generalDao.selectGeneralByArticleId(article_id);

    List<Files> files = new ArrayList<>();
    files.add(filesDao.selectFilesById(general.getFile1()));
    files.add(filesDao.selectFilesById(general.getFile2()));
    general.setFiles(files);
    article.setOption(general);

    model.addAttribute("article", article);

    return null;
  }

  @GetMapping("/update")
  public String updatePage(int article_id, Model model) {    
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    
    Article article = articleDao.selectOneArticle(article_id);

    String username = Helper.userName();

    if (username != article.getUsername()) {
      return "redirect:/"; // 403 페이지로 이후에 변경, security?
    }

    General general = generalDao.selectGeneralByArticleId(article_id);
    List<Files> files = new ArrayList<>();
    files.add(filesDao.selectFilesById(general.getFile1()));
    files.add(filesDao.selectFilesById(general.getFile2()));
    general.setFiles(files);
    article.setOption(general);
    
    model.addAttribute("article", article);

    return null;
  }

  @PostMapping("/update")
  public String updateArticle(Article article, List<MultipartFile> files) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Board board = boardDao.selectBoardById(article.getBoard_id());

    return "redirect:/myclass/board/read?article_id=" + article.getId();
  }

  @GetMapping("/delete")
  public String deleteArticle(int article_id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    Article article = articleDao.selectOneArticle(article_id);

    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Board board = boardDao.selectBoardById(article.getBoard_id());

    String username = Helper.userName();

    if (username != article.getUsername()) {
      return "redirect:/"; // 403 페이지로 이후에 변경
    }

    articleDao.deleteArticle(article_id);

    return "redirect:/myclass/board?board_id=" + board.getId();
  }
}