package heapStark.blogCode.jdbc.databasePool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public class databasePoolTest {
    private DruidDataSource source = PoolUtils.getPool();

    @Test
    public void test() throws SQLException {
        DruidPooledConnection connection = source.getConnection();
        Statement statement = connection.createStatement();
        String sql = "show tables";
        statement.execute(sql);
        ResultSet resultSet= statement.executeQuery(sql);
        resultSet.next();
        assert (resultSet.getString(1).equals("student"));
        //connection.commit();
    }
    @Test
    public void createTest() throws Exception{
        DruidPooledConnection connection = source.getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE `teacher` (\n" +
                "  `mysqlId` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `name` varchar(10) NOT NULL,\n" +
                "  `gender` int(11) DEFAULT NULL,\n" +
                "  `score` int(11) DEFAULT NULL,\n" +
                "  `age` int(11) DEFAULT NULL,\n" +
                "  `birthday` date DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`,`name`),\n" +
                "  KEY `mysqlId` (`mysqlId`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8\n";
        statement.execute(sql);

    }



    @Test
    public void insertTest() throws Exception{
        DruidPooledConnection connection = source.getConnection();
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO teacher (id, NAME,gender,score,age,birthday)VALUES('125','liu','0','123','15',NOW())";
        statement.execute(sql);

    }
}
