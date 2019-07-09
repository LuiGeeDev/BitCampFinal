package kr.or.bit.dao;

import kr.or.bit.model.TroubleShooting;

public interface TroubleShootingDao extends OptionDao {
  void insertTroubleShooting(TroubleShooting troubleshooting);
  
  void changeTroubleShootingStatus(int id);
  
  TroubleShooting selectTroubleShootingByArticleId(int article_id);
}
