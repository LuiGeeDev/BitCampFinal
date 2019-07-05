package kr.or.bit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.BoardAddRemove;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;
import kr.or.bit.model.Member;
import kr.or.bit.utils.Helper;

@Service
public class BoardService {
  private final int ARTICLES_IN_PAGE = 20;

  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private FileUploadService fileUploadService;

  private int start(int page) {
    return (page - 1) * ARTICLES_IN_PAGE + 1;
  }

  private int end(int page) {
    return page * ARTICLES_IN_PAGE;
  }
  
  private void setArticleList(List<Article> articleList) {
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    for (Article article : articleList) {
      List<Comment> commentList = commentDao.selectAllComment(article.getId());
      for (Comment comment : commentList) {
        comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      }
      
      article.setCommentlist(commentList);
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setOption(generalDao.selectGeneralByArticleId(article.getId()));
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));   
    }
  }

  public Board getBoardInfo(int board_id) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Board board = boardDao.selectBoardById(board_id);
    return board;
  }

  public List<Article> getArticlesByPage(int boardId, int page) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);

    List<Article> articleList = articleDao.selectArticlesByPage(boardId, start(page), end(page));
    setArticleList(articleList);

    return articleList;
  }

  public List<Article> getArticlesSorted(int boardId, int page, String sort) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    
    List<Article> articleList = articleDao.selectArticlesSorted(boardId, start(page), end(page));
    setArticleList(articleList);
    
    return articleList;
  }

  public List<Article> getArticlesBySearchWord(int boardId, int page, String search, String criteria) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    
    List<Article> articleList = null;
    if (criteria.startsWith("comment")) {
      articleList = articleDao.selectArticlesByComment(boardId, start(page), end(page), criteria, search);
    } else {
      articleList = articleDao.selectArticlesBySearchWord(boardId, start(page), end(page), criteria, search);
    }
    
    setArticleList(articleList);

    return articleList;
  }

  @PostAuthorize("hasAnyRole('TEACHER', 'MANAGER') or returnObject.username == principal.username")
  public Article getArticleForUpdateOrDelete(int id) {
    return readArticle(id);
  }

  @PreAuthorize("hasAnyRole('TEACHER', 'MANAGER') or #article.username == principal.username")
  public void updateArticle(Article article, List<MultipartFile> files) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleDao.updateArticle(article);
  }

  @PreAuthorize("hasAnyRole('TEACHER', 'MANAGER') or #article.username == principal.username")
  public void deleteArticle(Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(article.getId());
  }

  public Article readArticle(int article_id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    Article article = articleDao.selectOneArticle(article_id);
    General general = generalDao.selectGeneralByArticleId(article_id);

    List<Files> files = new ArrayList<>();
    files.add(filesDao.selectFilesById(general.getFile1()));
    files.add(filesDao.selectFilesById(general.getFile2()));
    general.setFiles(files);
    article.setOption(general);
    article.setTimeLocal(article.getTime().toLocalDateTime());
    List<Comment> commentList = commentDao.selectAllComment(article.getId());
    for (Comment comment : commentList) {
      comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
    }
    article.setCommentlist(commentList);
    article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));

    return article;
  }

  public int writeArticle(Article article, MultipartFile file1, MultipartFile file2, HttpServletRequest request) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);

    try {
      article.setUsername(Helper.userName());
      articleDao.insertArticle(article);

      List<MultipartFile> files = new ArrayList<>();
      files.add(file1);
      files.add(file2);

      List<Files> uploadedFiles = fileUploadService.uploadFile(files, request);
      General general = new General();

      for (Files file : uploadedFiles) {
        filesDao.insertFiles(file);
        Files insertedFile = filesDao.selectFilesByFilename(file.getFilename());
        if (general.getFile1() == 0) {
          general.setFile1(insertedFile.getId());
        } else {
          general.setFile2(insertedFile.getId());
        }
      }
      int insertedArticleId = articleDao.getMostRecentArticleId();
      general.setArticle_id(insertedArticleId);

      generalDao.insertGeneral(general);
    } catch (IllegalStateException e) {
      System.out.println("WriteArticle: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("WriteArticle: " + e.getMessage());
    }

    return articleDao.getMostRecentArticleId();
  }

  public void writeComment(int article_id, Comment comment) {
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    comment.setArticle_id(article_id);
    comment.setUsername(Helper.userName());
    commentDao.insertComment(comment);
  }

  public void deleteComment(int comment_id) {
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class);
    commentDao.deleteComment(comment_id);
  }

  public void decideBoardAddOrRemove(BoardAddRemove boardAddRemove) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);

    List<String> boardToAdd = boardAddRemove.getBoardToAdd();
    List<String> boardToRemove = boardAddRemove.getBoardToRemove();

    int course_id = memberDao.selectMemberByUsername(Helper.userName()).getCourse_id();

    for (String boardname : boardToAdd) {
      Board exists = boardDao.isBoardExists(course_id, boardname);
      if (exists == null) {
        Board board = new Board();
        board.setBoard_name(boardname);
        board.setBoardtype(3);
        board.setCourse_id(course_id);
        boardDao.insertBoard(board);
      }
    }

    for (String boardname : boardToRemove) {
      Board exists = boardDao.isBoardExists(course_id, boardname);
      if (exists != null) {
        boardDao.deleteBoard(exists.getId());
      }
    }
  }
}