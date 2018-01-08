package heapStark.blogCode.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/8.
 */
public class JdbcUtils {
    static {
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        } catch (ClassNotFoundException e1) {
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }
    }

    public static Connection getConnection(String databaseName){
        String url = "jdbc:mysql://localhost:3306/"+databaseName+"?useUnicode=true&characterEncoding=utf8";    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "qwer");
        } catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
