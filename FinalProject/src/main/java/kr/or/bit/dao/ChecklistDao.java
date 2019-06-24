package kr.or.bit.dao;

import kr.or.bit.model.Checklist;

public interface ChecklistDao {
  void insertChecklist(Checklist checklist);

  void updateChecklist();

  void deleteChecklist(int id);

  Checklist selectAllChecklist(int group_id);
}
