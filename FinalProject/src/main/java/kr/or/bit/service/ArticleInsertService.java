package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;

@Service
public class ArticleInsertService implements ArticleOri {
  @Autowired
  private SqlSession sqlSession;

  public void writeArticle(Article article, ArticleOption option) {// 글쓰기 처리시 게시글 객체와 객체을 입력하면 각각 테이블에 데이터 저장
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    String optionname = option.getClass().getName().toLowerCase().trim();
    
    if (optionname.equals("troubleshooting")) {
      TroubleShootingDao trobleshooting = sqlSession.getMapper(TroubleShootingDao.class);
      articledao.insertArticle(article);
      trobleshooting.insertTroubleShooting(option);
      
    } else if (optionname.equals("homework")) {
        HomeworkDao homework = sqlSession.getMapper(HomeworkDao.class);
        articledao.insertArticle(article);
        homework.insertHomework(option);
      
    } else if (optionname.equals("general")) {
        GeneralDao general = sqlSession.getMapper(GeneralDao.class);
        articledao.insertArticle(article);
        general.insertGeneral(option);
      
    }
  }
  
  public void writeQnaArticle(Article article) {//qna게시글 삭제하는 함수
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    QnaDao qna = sqlSession.getMapper(QnaDao.class);
    articledao.insertArticle(article);
    qna.insertQna(article.getId());
  }
}
