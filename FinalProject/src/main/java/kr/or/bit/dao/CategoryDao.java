package kr.or.bit.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.model.Category;

public interface CategoryDao {
  
  List<Category> selectCategoryByCourseid(@RequestParam("course_id") int course_id);
  
  void insertCategory(Category category);
  
  void deleteCategory(int id);
  
  void updateCategory(Category category);
}
