package kr.or.bit.dao;

import java.util.List;

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
  void write();

  List<Board> getList();

  Board getBoard(int id);

  void updateBoard(int id);
}
