package kr.or.bit.dao;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.General;

public interface GeneralDao extends OptionDao {
  void insertGeneral(General general);

  void updateGeneral(String filename);

  General selectGeneralByArticleId(int article_id);
}
