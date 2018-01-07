package heapStark.blogCode.jdbc.mybatis.mapper;

import heapStark.blogCode.jdk.Vo.Student;

import java.util.List;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public interface StudentMapper {
    //@Select("SELECT * FROM student WHERE id = #{id}")

    /**
     *
     * @param student
     * @return
     */
    Student select(Student student);

    /**
     *
     * @param student
     * @return
     */
    Long insert(Student student);

    Long insertList(List<Student> student);

}
