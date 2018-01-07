package heapStark.blogCode.jdbc.mybatis;

import heapStark.blogCode.jdbc.mybatis.mapper.StudentMapper;
import heapStark.blogCode.jdk.Vo.Student;
import heapStark.blogCode.utils.MultiThreadTestUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public class mybatisTest {
    private SqlSessionFactory sessionFactory = SessionFactory.getFactory();

    @Test
    public void selectTest() throws Exception {
        SqlSession session = sessionFactory.openSession(true);
        try {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            Student student = new Student();
            Student selectedStudent = mapper.select(student);
            System.out.println(student);
        } finally {
            session.close();
        }
    }

    @Test
    public void insertTest() {
        SqlSession session = sessionFactory.openSession(true);
        try {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            Student student = new Student();
            student.setId(120);
            Long selectedStudent = mapper.insert(student);
            System.out.println(selectedStudent);
        } finally {
            //session.commit();
            session.close();
        }

    }

    @Test
    public void commitTest() throws Exception {
        SqlSession session = sessionFactory.openSession(false);
        try {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            Student student = new Student();
            student.setId(120);
            Long selectedStudent = mapper.insertList(Arrays.asList(student));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.commit();
            session.close();
        }

    }

    @Test
    public void insertListTest() throws Exception {
        SqlSession session = sessionFactory.openSession(false);
        try {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            Student student = new Student();
            student.setId(120);
            Long selectedStudent = mapper.insertList(Arrays.asList(student));
            //session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //commit之后rollback失效
            //session.rollback();
            //session.commit();
            //未commit直接close则数据不会被写入
            session.close();
        }
    }

    @Test
    public void rollbackTest() throws Exception {

        SqlSession session = sessionFactory.openSession(true);
        try {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            Student student = new Student();
            student.setId(120);
            Long selectedStudent = mapper.insert(student);
            mapper.insert(new Student());
            MultiThreadTestUtil.multiThreadRun(()->{
                SqlSession session2 = sessionFactory.openSession(false);

                    StudentMapper mapper2 = session.getMapper(StudentMapper.class);
                    Student student2 = new Student();
                    student.setId(122);
                    try {
                        mapper.insert(student);
                        mapper.insert(new Student());
                    }catch (Exception e){
                        //回滚无效
                        session2.rollback();
                    }

                    //session2.commit();
            },1);
            //session.commit();
        } catch (Exception e) {
            session.rollback();
        } finally {
            TimeUnit.SECONDS.sleep(2);
            session.close();
        }

    }
}
