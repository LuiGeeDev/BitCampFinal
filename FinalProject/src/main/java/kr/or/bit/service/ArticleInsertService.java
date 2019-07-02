package kr.or.bit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import kr.or.bit.model.TroubleShooting;
import kr.or.bit.model.Video;
import kr.or.bit.utils.Helper;

@Component
public class ArticleInsertService {
  @Autowired
  private SqlSession sqlSession;

  private ArticleDao wArticle(Article article) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    articledao.insertArticle(article);
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

  public void writeArticle(Article article, ArticleOption option, List<MultipartFile> file,
      HttpServletRequest request) {
    FileUploadService fileupload = new FileUploadService();
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());
    List<Integer> id = new ArrayList<Integer>();
    try {
      List<Files> files = fileupload.uploadFile(file, request);
      FilesDao filesdao = sqlSession.getMapper(FilesDao.class);
      for (Files list : files) {
        filesdao.insertFiles(list);
        id.add(filesdao.selectFilesByOriginalFileName(list.getOriginal_filename()));
      }
      
      switch (optionname) {
      case "general":
        GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
        ArticleDao artidao = wArticle(article);
        General general = new General();
        general.setArticle_id(artidao.getMostRecentArticleId());
        for()
        generalDao.insertGeneral(general);
        break;
      case "homework":
        HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
        ArticleDao artidao2 = wArticle(article);
        option = homeworkDao.insertHomework(homework);
        break;
      default:
        break;
      }
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
