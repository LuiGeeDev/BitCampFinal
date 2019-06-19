package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Board;

public interface BoardDao {
  void write();
  List<Board> getList();
  Board getBoard(int id);
}
