package kr.or.bit.dao;

import kr.or.bit.model.Checklist;

public interface ChecklistDao {
  void insertChecklist(Checklist checklist);

  void updateChecklist(Checklist checklist, int id);

  void deleteChecklist(int id);

  Checklist selectAllChecklist(int group_id);
}
