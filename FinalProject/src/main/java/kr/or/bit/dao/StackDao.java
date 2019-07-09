package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.Tag;
import kr.or.bit.utils.Pager;

public interface StackDao {
  List<Article> selectStackArticles ();
  
  List<Tag> selectTagList(int article_id);
  
  int countAllStackArticle();
  
  int countStackArticleBySearchWord(@Param("boardSearch") String boardSearch);
  
  List<Article> selectAllStackArticle(Pager pager);
  
  List<Tag> showTagList();
  
  Tag selectTagByName(String tag);
  
  void insertTag(int tag_id, int article_id);
  
  List<Article> selectStackArticleByTitleOrContent(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectStackArticleByTitle(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectStackArticleByWriter(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
}
