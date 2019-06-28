package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;

@Service
class ArticleService {
  @Autowired
  private SqlSession sqlSession;
  
  public void updateArticle() {
    
  }

  public List<Article> selectAllArticle(int boardId) {//게시판아이디를 기준으로 게시글을 전부 불러오는 함수
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    List<Article> list = articledao.selectAllArticleByBoardId(boardId);
    return list; 
  }
  
  public Article selectOneArticle(int id) {
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    List<Comment> list = commentdao.selectAllComment(id); 
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    Article article = articledao.selectOneArticle(id);
    article.setCommentlist(list);
    return article;
  }
  
}
