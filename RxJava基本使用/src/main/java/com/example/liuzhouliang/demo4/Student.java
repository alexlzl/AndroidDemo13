package com.example.liuzhouliang.demo4;

import java.util.List;

/**
 * Created by liuzhouliang on 2017/9/14.
 */

public class Student {
    private String name;
    private int age;
    private List<Course> courses;

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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
