package kr.or.bit.dao;

import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.General;

public interface GeneralDao extends OptionDao {
  
  void insertGeneral(ArticleOption general);
  
  void updateGeneral(String filename);
  
  void deleteGeneral(int id);
  
  General selectAllGeneralByArticleId(int articleid);
}
