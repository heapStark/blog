package heapStark.blogCode.jdbc.mybatis;

import heapStark.blogCode.jdbc.mybatis.mapper.StudentMapper;
import heapStark.blogCode.jdk.Vo.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public class Test {
    private SqlSessionFactory sessionFactory = SessionFactory.getFactory();

    @org.junit.Test
    public void selectTest() throws Exception{
        SqlSession session = sessionFactory.openSession();
        try {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            Student student = new Student();
            Student selectedStudent = mapper.select(student);
            System.out.println(student);
        } finally {
            session.close();
        }
    }

}
