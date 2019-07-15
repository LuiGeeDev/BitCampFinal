package kr.or.bit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.GroupMemberDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;
import kr.or.bit.model.Homework;
import kr.or.bit.model.TroubleShooting;
import kr.or.bit.model.Video;
import kr.or.bit.utils.Helper;

@Component
public class ArticleInsertService {
  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private FileUploadService fileUploadService;
  @Autowired
  private TagService tagService;

  public void writeArticle(Article article) {// QNA작성시 사용합니다.
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    QnaDao qna = sqlSession.getMapper(QnaDao.class);
    articleDao.insertArticle(article);
    qna.insertQna(article.getId());
  }

  public void writeArticle(Article article, ArticleOption option) {// 글쓰기 처리시 게시글 객체와 객체을 입력하면 각각 테이블에 데이터 저장
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());

    articleDao.insertArticle(article);

    if (optionname.equals("troubleshooting")) {
      TroubleShootingDao troubleshootingDao = sqlSession.getMapper(TroubleShootingDao.class);
      GroupMemberDao groupmemberdao = sqlSession.getMapper(GroupMemberDao.class);
      String username = Helper.userName();
      int groupid = groupmemberdao.getGroupIdByUsername(username);
      TroubleShooting troubleshooting = (TroubleShooting) option;
      troubleshooting.setArticle_id(article.getId());
      troubleshooting.setGroup_id(groupid);
      troubleshootingDao.insertTroubleShooting(troubleshooting);
    } else if (optionname.equals("video")) {
      VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
      Video video = (Video) option;
      video.setArticle_id(article.getId());
      videoDao.insertVideo(video);
    }
  }

  @Transactional
  public void writeArticle(Article article, ArticleOption option, List<MultipartFile> file,
      HttpServletRequest request) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());
    if (file != null) {
      try {
        List<Integer> fileIds = new ArrayList<Integer>();
        List<Files> files = fileUploadService.uploadFile(file, request);

        for (Files list : files) {
          filesDao.insertFiles(list);
          fileIds.add(filesDao.selectFilesByFilename(list.getFilename()).getId());
        }

        articleDao.insertArticle(article);

        switch (optionname) {
        case "general":
          General general = (General) option;
          general.setArticle_id(article.getId());

          for (int id : fileIds) {
            if (general.getFile1() == 0) {
              general.setFile1(id);
            } else {
              general.setFile2(id);
            }
          }

          generalDao.insertGeneral(general);
          break;
        case "homework":
          Homework homework = (Homework) option;
          homework.setArticle_id(article.getId());

          for (int id : fileIds) {
            if (homework.getFile1() == 0) {
              homework.setFile1(id);
            } else {
              homework.setFile2(id);
            }
          }

          homeworkDao.insertHomework(homework);
          break;
        default:
          break;
        }
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      articleDao.insertArticle(article);
      Homework homework = (Homework) option;
      homework.setArticle_id(article.getId());
      homeworkDao.insertHomework(homework);
    }
  }

  @Transactional
  public void writeReplyArticle(Article article, ArticleOption option, List<MultipartFile> file,
      HttpServletRequest request) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());

    if (file != null) {
      try {
        List<Integer> fileIds = new ArrayList<Integer>();
        List<Files> files = fileUploadService.uploadFile(file, request);
        FilesDao filesdao = sqlSession.getMapper(FilesDao.class);
        for (Files list : files) {
          filesdao.insertFiles(list);
          fileIds.add(filesdao.selectFilesByFilename(list.getFilename()).getId());
        }
        articleDao.insertReplyArticle(article);
        switch (optionname) {
        case "general":
          General general = (General) option;
          general.setArticle_id(article.getId());
          for (int id : fileIds) {
            if (general.getFile1() == 0) {
              general.setFile1(id);
            } else {
              general.setFile2(id);
            }
          }
          generalDao.insertGeneral(general);
          break;
        case "homework":
          Homework homework = (Homework) option;
          homework.setArticle_id(article.getId());
          for (int id : fileIds) {
            if (homework.getFile1() == 0) {
              homework.setFile1(id);
            } else {
              homework.setFile2(id);
            }
          }
          homeworkDao.insertHomework(homework);
          break;
        default:
          break;
        }
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      articleDao.insertArticle(article);
      Homework homework = (Homework) option;
      homework.setArticle_id(article.getId());
      homeworkDao.insertHomework(homework);
    }
  }

  // 스택 .....
  public void writeStackArticle(Article article, List<String> tagList) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    QnaDao qna = sqlSession.getMapper(QnaDao.class);
    articleDao.insertArticle(article);
    qna.insertQna(article.getId());
    tagService.insertTag(tagList, article.getId());
  }

  // Qna .......
  public void writeQnaArticle(Article article, List<String> tagList) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    QnaDao qna = sqlSession.getMapper(QnaDao.class);
    articleDao.insertArticle(article);

    qna.insertQna(article.getId());
    tagService.insertTag(tagList, article.getId());
  }

}
