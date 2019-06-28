package kr.or.bit.dao;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.ArticleOption;

public interface ArticleOptionDao {
  ArticleOption selectOption(@Param("table_name") String tableName, @Param("article_id") int article_id);
}
