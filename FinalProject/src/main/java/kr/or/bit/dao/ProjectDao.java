package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Project;

/*
*
* @date: 2019. 6. 21.
*
* @author: 이힘찬 
*
* @description: ProjectDao 
* 
*/
public interface ProjectDao {
  void insertProject(Project project);

  void deleteProject(int course_id);

  void updateProject(int course_id);

  List<Project> selectAllProject(int course_id);
}
