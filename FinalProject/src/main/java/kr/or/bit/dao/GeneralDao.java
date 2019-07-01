package kr.or.bit.dao;

import kr.or.bit.model.General;

public interface GeneralDao extends OptionDao {
  void insertGeneral(General general);

  void updateGeneral(String filename);

  General selectGeneralByArticleId(int article_id);
}
