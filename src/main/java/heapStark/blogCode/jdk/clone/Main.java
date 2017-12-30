package heapStark.blogCode.jdk.clone;

import heapStark.blogCode.jdk.Vo.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * blogcode
 * Created by wangzhilei3 on 2017/12/30.
 */
public class Main {
    @Test
    public void cloneTest() throws Exception {
        Student student = new Student("laowang", 18, new Date());
        Student studentClone = (Student) student.clone();
        assert (student.getBirthday() != studentClone.getBirthday());//默认浅拷贝,修改实现为深拷贝
        assert (student != studentClone);

        ArrayList<Student> studentArrayList = new ArrayList<>();
        studentArrayList.add(student);
        ArrayList<Student> studentArrayListClone = (ArrayList<Student>) studentArrayList.clone();
        assert (studentArrayList.get(0)==studentArrayListClone.get(0));//集合类默认实现的是浅拷贝
    }
}
