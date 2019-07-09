package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Article;
import kr.or.bit.model.Tag;

public interface StackDao {
  List<Article> selectStackArticles ();
  
  List<Tag> selectTagList(int article_id);
  
  int countArticle();
  
}
