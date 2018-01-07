package heapStark.blogCode.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public class JDBCTest {
    @Test
    public void testShow() {
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        } catch (ClassNotFoundException e1) {
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "root", "qwer");
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            String showTables = "show tables";
            ResultSet resultSet = stmt.executeQuery(showTables);
            while (resultSet.next()) {
                String s = resultSet.getString(1);
                assert (s.equals("student"));
            }
            System.out.print("成功连接到数据库！");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
