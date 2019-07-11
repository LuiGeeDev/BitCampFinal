package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Checklist;

public interface ChecklistDao {
  void insertChecklist(Checklist checklist);

  void updateChecklist(Checklist checklist);

  void deleteChecklist(int id);

  List<Checklist> selectAllChecklist(int group_id);
  
  void checkUpdate(@Param("id") int id, @Param("username") String username);
  
  Checklist selectOneChecklist(int id);
}
