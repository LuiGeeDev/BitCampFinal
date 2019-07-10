package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.model.General;
import kr.or.bit.utils.Pager;

public interface GeneralDao extends OptionDao {
  void insertGeneral(General general);

  void updateGeneral(General general);
  
  void updateFileOneGeneral(General general);
  
  void updateFileTwoGeneral(General general);

  General selectGeneralByArticleId(int article_id);
  
  int countAllGeneralArticlesByBoardId(int board_id);
  
  int countAllGeneralArticlesByBoardIdAndTitleOrContent(@Param("board_id") int board_id, @Param("boardSearch") String boardSearch);
  
  int countAllGeneralArticlesByBoardIdAndTitle(@Param("board_id") int board_id, @Param("boardSearch") String boardSearch);
  
  int countAllGeneralArticlesByBoardIdAndWriter(@Param("board_id") int board_id, @Param("boardSearch") String boardSearch);
  
  List<Article> selectGeneralArticlesByTitleOrContent(@Param("board_id") int board_id, @Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectGeneralArticlesByTitle(@Param("board_id") int board_id, @Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
  
  List<Article> selectGeneralArticlesByWriter(@Param("board_id") int board_id, @Param("pager") Pager pager, @Param("boardSearch") String boardSearch);
}
