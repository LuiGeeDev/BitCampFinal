package kr.or.bit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.or.bit.dao.StackDao;
import kr.or.bit.model.Tag;

@Component
public class TagService {
  @Autowired
  private SqlSession sqlSession;

  // 이름만 있는 태그들로 id,color,tag가 들어있는 Tag객체를 찾고
  // 만들어진 Tag객체를 List<Tag> 에 담는 과정이 있는 서비스입니다...^0^
  public List<Tag> selectTagByName(List<String> tagList) {
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    List<Tag> tags = new ArrayList<>();
    for(int i=0; i<tagList.size(); i++) {
    String tag = tagList.get(i);
    Tag onetag = stackdao.selectTagByName(tag);
    tags.add(onetag);
    }
    return tags;
    
  }
}
