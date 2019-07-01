package kr.or.bit.dao;

import kr.or.bit.model.TroubleShooting;

public interface TroubleShootingDao extends OptionDao {
  void insertTroubleShooting(TroubleShooting troubleshooting);
  
  void updateTroubleShootingByIssueClosed(TroubleShooting troubelshooting);
  
  TroubleShooting selectTroubleShootingByArticleId(int article_id);
}
