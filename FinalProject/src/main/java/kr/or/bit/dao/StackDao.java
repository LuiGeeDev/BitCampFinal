package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Article;
import kr.or.bit.model.Tag;

public interface StackDao {
  List<Article> selectStackArticles ();
  
  List<Tag> selectTagList(int article_id);
  
  int countArticle();
  
  List<Tag> showTagList();
  
  Tag selectTagByName(String tag);
  
  void insertTag(int tag_id, int article_id);
  
}
