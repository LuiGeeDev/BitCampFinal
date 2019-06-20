package kr.or.bit.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class NewsService {
  /*
   * @method Name: getNews
   * 
   * @date: 2019. 6. 19.
   * 
   * @author: 윤종석
   * 
   * @description: 네이버 뉴스 API를 이용, 뉴스 정보를 JSON 형태의 문자열로 가져온다(https://developers.naver.com/docs/search/news/ 참고)
   * 
   * @param spec: none
   * 
   * @return: String
   */
  public String getNews() {
    // API 접속을 위한 ID와 비밀번호
    String clientId = "vqLcW7JqiYZue0zeuYqs";
    String clientSecret = "3ItRGLhcxI";
    final int DISPLAY = 3; // 뉴스를 가져올 숫자(최대 100개)
    
    // 뉴스 결과를 담을 StringBuffer
    StringBuffer resp = new StringBuffer();
    try {
      // 뉴스 검색어를 UTF-8 인코딩으로 전환, 검색어에 제공
      String text = URLEncoder.encode("프로그래머", "UTF-8");
      // API에 요청
      String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=" + DISPLAY;
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("X-Naver-Client-Id", clientId);
      con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
      int responseCode = con.getResponseCode();
      BufferedReader br;
      if (responseCode == 200) {
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      while ((inputLine = br.readLine()) != null) {
        resp.append(inputLine);
      }
      br.close();
    } catch (UnsupportedEncodingException e) {
      System.out.println("뉴스: " + e.getMessage());
    } catch (MalformedURLException e) {
      System.out.println("뉴스: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("뉴스: " + e.getMessage());
    }
    
    return resp.toString();
  }
}
