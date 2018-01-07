package heapStark.blogCode.jdbc.mybatis.mapper;

import heapStark.blogCode.jdk.Vo.Student;
import org.apache.ibatis.annotations.Select;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/7.
 */
public interface StudentMapper {
    //@Select("SELECT * FROM student WHERE id = #{id}")
    Student select(Student student);
}
