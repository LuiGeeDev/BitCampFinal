package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Category;

public interface CategoryDao {
  
  List<Category> selectCategoryByCourseid(int courseid);
  
  void insertCategory(Category category);
  
  void deleteCategory(int id);
  
  void updateCategory(Category category);
}
