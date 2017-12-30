package heapStark.blogCode.jdk.Vo;

import java.util.Date;

/**
 * blogcode
 * Created by wangzhilei3 on 2017/12/30.
 */
public class Student implements Cloneable{
    @Override
    public Object clone() throws CloneNotSupportedException {
        Student student =  (Student)super.clone();
        student.setBirthday((Date) student.birthday.clone());
        return student;
    }

    private  String name;
    private int age;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Student(String name, int age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public Student() {
        this(null,0,new Date());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
