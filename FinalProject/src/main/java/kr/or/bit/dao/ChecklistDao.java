package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Checklist;

public interface ChecklistDao {
  void insertChecklist(Checklist checklist);

  void updateChecklist(Checklist checklist);

  void deleteChecklist(int id);

  List<Checklist> selectAllChecklist(int group_id);
}
