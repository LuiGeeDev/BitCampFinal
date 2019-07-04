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

  private ArticleDao wArticle(Article article) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    articledao.insertArticle(article);
    return articledao;
  }
  
  public ArticleDao writeReplyArticle(Article article) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    articledao.insertReplyArticle(article);
    return articledao;
  }

  public void writeArticle(Article article) {// QNA작성시 사용합니다.
    ArticleDao artidao = wArticle(article);
    QnaDao qna = sqlSession.getMapper(QnaDao.class);
    int recentId = artidao.getMostRecentArticleId();
    qna.insertQna(recentId);
  }

  public void writeArticle(Article article, ArticleOption option) {// 글쓰기 처리시 게시글 객체와 객체을 입력하면 각각 테이블에 데이터 저장
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());
    System.out.println(optionname);
    ArticleDao artidao = wArticle(article);
    if (optionname.equals("troubleshooting")) {
      TroubleShootingDao troubleshootingDao = sqlSession.getMapper(TroubleShootingDao.class);
      GroupMemberDao groupmemberdao = sqlSession.getMapper(GroupMemberDao.class);
      String username = Helper.userName();
      int articleid = artidao.getMostRecentArticleId();
      int groupid = groupmemberdao.getGroupIdByUsername(username);
      TroubleShooting troubleshooting = (TroubleShooting) option;
      troubleshooting.setArticle_id(articleid);
      troubleshooting.setGroup_id(groupid);
      troubleshootingDao.insertTroubleShooting(troubleshooting);
    } else if (optionname.equals("video")) {
      VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
      int articleid = artidao.getMostRecentArticleId();
      Video video = (Video) option;
      video.setArticle_id(articleid);
      videoDao.insertVideo(video);
    }
  }

  @Transactional
  public void writeArticle(Article article, ArticleOption option, List<MultipartFile> file,
      HttpServletRequest request) {
    
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
        ArticleDao artidao = wArticle(article);
        switch (optionname) {
        case "general":
          GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
          General general = (General) option;
          general.setArticle_id(artidao.getMostRecentArticleId());
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
          HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
          Homework homework = (Homework) option;
          homework.setArticle_id(artidao.getMostRecentArticleId());
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
    }else {
      ArticleDao artidao = wArticle(article);
      HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
      Homework homework = (Homework) option;
      homework.setArticle_id(artidao.getMostRecentArticleId());
      homeworkDao.insertHomework(homework);
    }
  }
  
  @Transactional
  public void writeReplyArticle(Article article, ArticleOption option, List<MultipartFile> file,
      HttpServletRequest request) {
    
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
        ArticleDao artidao = writeReplyArticle(article);
        switch (optionname) {
        case "general":
          GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
          General general = (General) option;
          general.setArticle_id(artidao.getMostRecentArticleId());
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
          HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
          Homework homework = (Homework) option;
          homework.setArticle_id(artidao.getMostRecentArticleId());
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
    }else {
      ArticleDao artidao = wArticle(article);
      HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
      Homework homework = (Homework) option;
      homework.setArticle_id(artidao.getMostRecentArticleId());
      homeworkDao.insertHomework(homework);
    }
  }

}
