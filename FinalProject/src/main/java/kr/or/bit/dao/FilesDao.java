package kr.or.bit.dao;

import kr.or.bit.model.Files;

public interface FilesDao {
  
  void insertFiles(FilesDao files);
  
  void updateFiles(FilesDao files);
  
  void deleteFiles(int id);
  
  Files selectFilesById(int id);
}
