package kr.or.bit.dao;

public interface FilesDao {
  
  void insertFiles(FilesDao files);
  
  void updateFiles(FilesDao files);
  
  void deleteFiles(int id);
  
  FilesDao selectFilesById(int id);
}
