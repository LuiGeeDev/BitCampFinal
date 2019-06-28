package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;

@Service
public class ArticleDeleteService implements ArticleOri {
  
  @Autowired
  private SqlSession sqlSession;
  
  public void deleteArticle(String articleId, String boardOption) {//게시글번호와 게시판 종류를 입력하면 게시글을 삭제하는 함수
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    String bo = boardOption.trim().toLowerCase();
    int artiId = Integer.parseInt(articleId);
    
    if (bo.equals("qna")) {
      articledao.deleteArticle(artiId);
      
    } else if (bo.equals("troubleshooting")) {
      articledao.deleteArticle(artiId);

    } else if (bo.equals("homework")) {
      articledao.deleteArticle(artiId);

    } else if (bo.equals("general")) {
      articledao.deleteArticle(artiId);
      
    } else if (bo.equals("vidio")){
      articledao.deleteArticle(artiId);
    }
  }
}
