package heapStark.blogCode.jdbc.mybatis;

import heapStark.blogCode.jdbc.databasePool.PoolUtils;
import heapStark.blogCode.jdbc.mybatis.mapper.StudentMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public class SessionFactory {
    static DataSource dataSource = PoolUtils.getPool();
    static TransactionFactory transactionFactory = new JdbcTransactionFactory();
    static Environment environment = new Environment("development", transactionFactory, dataSource);
    static Configuration configuration = new Configuration(environment);
    static {
        //configuration.addMapper(StudentMapper.class);
        configuration.addMappers("heapStark");
    }
    static SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configuration);

    static public SqlSessionFactory getFactory (){
        return factory;
    }

}
