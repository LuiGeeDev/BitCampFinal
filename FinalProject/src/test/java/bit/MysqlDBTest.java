package bit;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;





public class MysqlDBTest {
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String URL = "jdbc:mysql://springuser.ctlyt0hp6bao.ap-northeast-2.rds.amazonaws.com:3306/gcloud";
  private static final String USER = "springuser";
  private static final String PASSWORD = "10041004";

  @Test
  public void testConnection() throws Exception {
    Class.forName(DRIVER);
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
      System.out.println("성공");
      System.out.println(con);
    } catch (Exception e) {
      System.out.println("에러발생");
      e.printStackTrace();
    }
  }
}
