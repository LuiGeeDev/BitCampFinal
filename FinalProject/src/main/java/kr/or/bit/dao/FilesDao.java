package kr.or.bit.dao;

import kr.or.bit.model.Files;

public interface FilesDao {
  
  void insertFiles(Files files);
  
  void updateFiles(Files files);
  
  void deleteFiles(int id);
  
  int selectFilesByFilename(String filename);

  Files selectFilesById(int id);
}
