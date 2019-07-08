package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Board;

/*
*
* @date: 2019. 06. 21.
*
* @author: 권예지
*
* @description: BoardDao를 통해서 Board CRUD를 이용한다
* 
*/
public interface BoardDao {
  void updateBoard(int id);
  
  List<Board> selectMyClassBoard(int course_id);

  void insertBoard(Board board);
  
  Board selectBoardByCourseId(@Param("course_id")int course_id,@Param("boardtype") int boardType);

  Board selectBoardById(int board_id);

  Board isBoardExists(@Param("course_id") int course_id, @Param("board_name") String board_name);

  void deleteBoard(int board_id);
}
