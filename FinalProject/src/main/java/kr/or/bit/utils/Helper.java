package kr.or.bit.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Helper {
  
   public static String userName() {//로그인된 사람의 맴버 객체를 뽑아주는 함수     
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     String username = userDetails.getUsername();
          
     return username;
   }
   
   public static int articleId() {//게시글 아이디를 뽑아주는 함수
      int i = 0;
      
      return i;
   }
}
