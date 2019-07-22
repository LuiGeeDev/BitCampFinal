package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Tag;
import kr.or.bit.utils.Pager;

public interface StackDao {
  List<Article> selectStackArticles ();
  
  List<Tag> selectTagList(int article_id);
  
  int countAllStackArticle();
  
  int countStackArticleByTitleOrContent(@Param("boardSearch") String boardSearch);
  
  int countStackArticleByTitle(@Param("boardSearch") String boardSearch);
  
  int countStackArticleByWriter(@Param("boardSearch") String boardSearch);
  
  List<Article> selectAllStackArticle(Pager pager);
  
  List<Tag> showTagList();
  
  void plusTag(@Param("tag") String tag, @Param("color") String color);
  
  void deleteTag(String tag);
  
  //tag 삭제시 밑에 두개 사용;
  void deleteArticlebyTag(String tag);
  
  void deleteTagName (String tag);
  
  Tag selectTagByName(String tag);
  
  void insertTag(@Param("tag_id") int tag_id,@Param("article_id") int article_id);
  
  List<Article> selectStackArticleByTitleOrContent(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectStackArticleByTitle(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectStackArticleByWriter(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);

  List<Article> selectStackArticleByTag(@Param("pager") Pager pager, @Param("boardSearch") String boardSearch);

  Comment selectAdoptedAnswer(int id);

  void deleteTag(int article_id);
}
