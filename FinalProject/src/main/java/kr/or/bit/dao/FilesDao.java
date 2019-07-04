package kr.or.bit.dao;

import kr.or.bit.model.Files;

public interface FilesDao {
  
  void insertFiles(Files files);
  
  void updateFiles(Files files);
  
  void deleteFiles(int id);
  
  Files selectFilesByFilename(String filename);

  Files selectFilenameById(int id);
  
  Files selectFilesById(int id);
}
