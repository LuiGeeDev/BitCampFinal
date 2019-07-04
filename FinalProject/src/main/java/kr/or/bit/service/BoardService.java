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
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Board;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;

@Service
public class BoardService {
  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private FileUploadService fileUploadService;

  public Board getBoardInfo(int board_id) {
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    Board board = boardDao.selectBoardById(board_id);
    return board;
  }

  public List<Article> getArticlesByPage(int boardId, int page) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    List<Article> articleList = articleDao.selectArticlesByPage(boardId, (page - 1) * 20, page * 20);
    return articleList;
  }

  public List<Article> getArticlesSorted(int boardId, int page, String sort) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    return null;
  }

  public List<Article> getArticlesBySearchWord(int boardId, int page, String search) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    return null;
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

    Article article = articleDao.selectOneArticle(article_id);
    General general = generalDao.selectGeneralByArticleId(article_id);

    List<Files> files = new ArrayList<>();
    files.add(filesDao.selectFilesById(general.getFile1()));
    files.add(filesDao.selectFilesById(general.getFile2()));
    general.setFiles(files);
    article.setOption(general);

    return article;
  }

  public int writeArticle(Article article, MultipartFile file1, MultipartFile file2, HttpServletRequest request) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);

    try {
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
}