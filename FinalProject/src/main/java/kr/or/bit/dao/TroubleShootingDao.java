package kr.or.bit.dao;

import kr.or.bit.model.ArticleOption;

public interface TroubleShootingDao extends OptionDao {
  
  void insertTroubleShooting(ArticleOption troubleshooting);
  
  void updateTroubleShootingByIssueClosed(int issueclosed);
  
  void selectTroubleShootingByArticleId(int articleid);
}
