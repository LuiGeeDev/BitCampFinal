package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.General;
import kr.or.bit.utils.Helper;

/**
 * 글 옮겨오기용 컨트롤러
 */
@Controller
public class CopyController {
  @Autowired
  private SqlSession sqlSession;

  /**
   * 게시판을 클릭하면 글 목록의 첫 부분을 응답하는 함수
   * 
   * @param id: 게시판 ID
   */
  @PostMapping("/copy/articles")
  public @ResponseBody List<Article> getArticles(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Article> articles = articleDao.selectFirstArticlesToCopyByBoardId(id);
    for (Article article : articles) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }

    return articles;
  }

  /**
   * 글 스크롤을 가장 밑으로 내리면 글을 추가로 불러오는 함수
   * 
   * @param board_id: 게시판 번호
   * @param id: 마지막으로 가져왔던 글 번호
   */
  @PostMapping("/copy/more")
  public @ResponseBody List<Article> getMoreArticles(int board_id, int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Article> articles = articleDao.selectNextArticlesToCopyByBoardId(board_id, id);
    for (Article article : articles) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }

    return articles;
  }

  /**
   * 글을 클릭하면 글 내용을 보여주기 위해 글을 가져오는 함수
   * 
   * @param id: 글 번호
   */
  @PostMapping("/copy/article")
  public @ResponseBody Article getArticle(int id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Article article = articleDao.selectOneArticle(id);
    article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    article.setTimeLocal(article.getTime().toLocalDateTime());

    return article;
  }

  /**
   * 검색하면 제목을 기준으로 검색해서 글 목록을 응답하는 함수
   * 
   * @param board_id: 게시판 번호
   * @param title: 검색어
   */
  @PostMapping("/copy/search")
  public @ResponseBody List<Article> searchArticle(int board_id, String title) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    List<Article> searchArticleList = articleDao.selectSearchTitleByBoardId(board_id, title);
    for (Article article : searchArticleList) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
    }
    return searchArticleList;
  }

  /**
   * 글을 새로운 게시판에 옮겨서 작성하는 기능
   * 
   * @param board_id: 게시판 번호
   * @param ids: 옮길 글 번호의 목록
   * 
   * ids: HTML에서 ,로 구분한 숫자롤 전달함. 이때는 반드시 @RequestParam이 필요
   */
  @GetMapping("/copy/copy")
  @Transactional
  public String copyArticle(int board_id, @RequestParam(name = "ids", required = false) List<Integer> ids) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);

    for (int id : ids) {
      Article oldArticle = articleDao.selectOneArticle(id);
      oldArticle.setUsername(Helper.userName());
      oldArticle.setBoard_id(board_id);
      articleDao.insertArticle(oldArticle);

      General general = generalDao.selectGeneralByArticleId(id);
      general.setArticle_id(oldArticle.getId());
      generalDao.insertGeneral(general);
    }

    return "redirect:/myclass/board?board_id=" + board_id;
  }
}
