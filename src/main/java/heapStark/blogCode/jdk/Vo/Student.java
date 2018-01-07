package heapStark.blogCode.jdk.Vo;

import java.util.Date;

/**
 * blogcode
 * Created by wangzhilei3 on 2017/12/30.
 */
public class Student implements Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        student.setBirthday((Date) student.birthday.clone());
        return student;
    }

    private String name;
    private int age;
    private Date birthday;
    private int id;
    private int score;
    private int gender;


    public Student() {
        this("wang", 0, new Date(), 110, 123, 1);
    }

    public Student(String name, int age, Date birthday, int id, int score, int gender) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.id = id;
        this.score = score;
        this.gender = gender;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Student{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", birthday=").append(birthday);
        sb.append(", it=").append(id);
        sb.append(", score=").append(score);
        sb.append(", gender=").append(gender);
        sb.append('}');
        return sb.toString();
    }
}
