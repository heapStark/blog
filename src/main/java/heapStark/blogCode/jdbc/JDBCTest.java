package heapStark.blogCode.jdbc;

import heapStark.blogCode.jdbc.utils.JdbcUtils;
import heapStark.blogCode.utils.MultiThreadTestUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public class JDBCTest {
    @Test
    public void testShow() {
        Connection conn = JdbcUtils.getConnection("transaction");
        try {
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            String sql = "show tables";
            ResultSet resultSet = stmt.executeQuery(sql);
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

    /**
     * autoCommit 测试
     */
    @Test
    public void insertTest() {
        Connection conn = JdbcUtils.getConnection("transaction");
        try {
            assert (conn.getAutoCommit() == true);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("成功连接到数据库！");
        try {
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            String sql = "INSERT INTO student (id, NAME,gender,score,age,birthday)VALUES('125','liu','0','123','15',NOW())";
            boolean result = stmt.execute(sql);
            System.out.print("执行结果！" + result);
            stmt.close();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modifyTest() throws Exception {
        Connection conn = JdbcUtils.getConnection("transaction");

        System.out.println("成功连接到数据库！");

        //创建一个Statement对象
        Statement stmt = conn.createStatement(); //创建Statement对象
        String sql = "UPDATE student SET id =221 WHERE id = 220";
        int result = stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }


    /**
     * 脏读测试 不可重复读 Connection.TRANSACTION_READ_UNCOMMITTED
     * @throws Exception
     */
    @Test
    public void dirtyReadTest() throws Exception {
        MultiThreadTestUtil.multiThreadRun(() -> {

            Connection conn = JdbcUtils.getConnection("transaction");
            try {
                assert (conn.getAutoCommit() == true);
                conn.setAutoCommit(false);
                //conn.setTransactionIsolation(Connection.TRANSACTION_NONE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("成功连接到数据库！");
            try {
                //创建一个Statement对象
                Statement stmt = conn.createStatement(); //创建Statement对象
                String sql = "UPDATE student SET id =221 WHERE id = 220";
                int result = stmt.executeUpdate(sql);
                System.out.println("修改执行结果：" + result);
                stmt.close();
                TimeUnit.SECONDS.sleep(7);
                conn.rollback();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1);
        //脏读读取未提交数据
        Connection conn = JdbcUtils.getConnection("transaction");
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        System.out.println("成功连接到数据库！");
        try {
            TimeUnit.SECONDS.sleep(2);
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            String sql ="SELECT * FROM student WHERE id = 221";
            ResultSet result = stmt.executeQuery(sql);
            result.next();
            assert (result.getString("id").equals("221"));
            System.out.println("执行结果！" + result.getString("id"));
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //不读取未提交数据
        Connection conn2 = JdbcUtils.getConnection("transaction");
        conn2.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        System.out.println("成功连接到数据库！");
        try {
            TimeUnit.SECONDS.sleep(2);
            //创建一个Statement对象
            Statement stmt = conn2.createStatement(); //创建Statement对象
            String sql ="SELECT * FROM student WHERE id = 220";
            ResultSet result = stmt.executeQuery(sql);
            result.next();
            assert (result.getString("id").equals("220"));
            System.out.println("执行结果！" + result.getString("id"));
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 幻读测试
     * @throws Exception
     */
    @Test
    public void committedReadTest() throws Exception {

    }
}
