package kr.or.bit.config;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;

@Configuration
@EnableAsync
@EnableScheduling
public class ScheduledConfig {
  @Autowired
  SqlSession sqlSession;
  
  @Scheduled(cron = "0 01 00 * * ?")
  public void scheduleCheckMember() {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    List<Member> memberList = memberDao.selectStudent();
    
    
    for(int i = 0 ; i < memberList.size() ; i ++) {
      if(new java.util.Date(memberList.get(i).getEnd_date().getTime()).compareTo(new java.util.Date()) == -1
         && !memberList.get(i).getRole().equals("TEACHER")) {
        memberDao.updateMemberGraduate(memberList.get(i));
      }
    }
    
  }
}
