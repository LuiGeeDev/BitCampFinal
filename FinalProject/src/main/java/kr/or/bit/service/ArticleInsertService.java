package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.GroupMemberDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.General;
import kr.or.bit.model.TroubleShooting;
import kr.or.bit.model.Video;
import kr.or.bit.utils.Helper;

@Component
public class ArticleInsertService {
  
  @Autowired
  private SqlSession sqlSession;

  public void writeArticle(Article article, ArticleOption option) {// 글쓰기 처리시 게시글 객체와 객체을 입력하면 각각 테이블에 데이터 저장
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());
    System.out.println(optionname);
    articledao.insertArticle(article);
    
    if (optionname.equals("troubleshooting")) {
      TroubleShootingDao troubleshootingDao = sqlSession.getMapper(TroubleShootingDao.class);
      GroupMemberDao groupmemberdao = sqlSession.getMapper(GroupMemberDao.class);
      
      String username = Helper.userName();
      int articleid = articledao.getMostRecentArticleId();
      int groupid = groupmemberdao.getGroupIdByUsername(username);
      
      TroubleShooting troubleshooting = (TroubleShooting)option;
      troubleshooting.setArticle_id(articleid);
      troubleshooting.setGroup_id(groupid);
      
      troubleshootingDao.insertTroubleShooting(troubleshooting);
      
      
    } else if (optionname.equals("homework")) {
        HomeworkDao homework = sqlSession.getMapper(HomeworkDao.class);
        FileUploadService fileUploadService = new FileUploadService();
        
    } else if (optionname.equals("general")) {
        GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
        //파일저장 부분 미구현
        
        //
        int id = articledao.getMostRecentArticleId();
        General general = (General)option;
        general.setArticle_id(id);
        generalDao.insertGeneral(general);
    } else if(optionname.equals("video")) {
        VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
        int id = articledao.getMostRecentArticleId();
        Video video = (Video) option;
        video.setArticle_id(id);
        videoDao.insertVideo(video);
    }
  }

  public void writeQnaArticle(Article article) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    QnaDao qna = sqlSession.getMapper(QnaDao.class);
    articledao.insertArticle(article);
    qna.insertQna(article.getId());
  }
}
