package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Article;

/*
 *
 * @date: 2019. 6. 21.
 *
 * @author: 이힘찬 
 *
 * @description: ArticleDao
 *
 */
public interface ArticleDao {
  void insertArticle(Article article);

  void updateArticle(Article article, int id);

  void deleteArticle(int id);

  List<Article> selectAllArticle();
}
