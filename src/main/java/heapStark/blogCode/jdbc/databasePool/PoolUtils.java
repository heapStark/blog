package heapStark.blogCode.jdbc.databasePool;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public class PoolUtils {
    static DruidDataSource pool = new DruidDataSource();
    static {
        pool.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8");
        pool.setPassword("qwer");
        pool.setUsername("root");
        try {
            pool.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DruidDataSource getPool(){
        return pool;
    }


}
