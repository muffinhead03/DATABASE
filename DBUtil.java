package DB2025Team09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	//데이터 베이스 연결을 위한 코드입니다.
	
    private static final String URL = "jdbc:mysql://localhost:3306/DB2025Team09?serverTimezone=Asia/Seoul";  //데이터 베이스 URL
    private static final String USER = "DB2025Team09";  // 데이터베이스 유저 id
    private static final String PASSWORD = "DB2025Team09"; // 데이터베이스 암호

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버는 한 번만 로드하면 됨
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

