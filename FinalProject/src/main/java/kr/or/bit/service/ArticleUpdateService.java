package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Video;


@Service
public class ArticleUpdateService implements ArticleOri {
  @Autowired
  private SqlSession sqlSession;
  
  public void updateArticle(Article article, ArticleOption option) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());
    int articleid = article.getId();
    articledao.updateArticle(article);
    
    if (optionname.equals("homework")) {
        System.out.println("피카");
        System.out.println("아직 ㄴ");
    } else if (optionname.equals("general")) {
        System.out.println("피카");
        System.out.println("아직 ㄴ");
    } else if(optionname.equals("video")) {
      VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
      Video video = (Video) option;
      video.setArticle_id(articleid);
      videoDao.updateVideo(video);
    }
  }
  
  public void updateTroubleShooting(int id ) {

  }
  
  public void updateQna() {
    QnaDao qnadao = sqlSession.getMapper(QnaDao.class);
    qnadao.updateQnaByAnswered();
  }
  
  public void updateQnaT() {
    QnaDao qnadao = sqlSession.getMapper(QnaDao.class);
    qnadao.updateQnaByTeacherAnswered();
  }
}
