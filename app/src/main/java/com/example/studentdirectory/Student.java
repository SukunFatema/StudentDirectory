package com.example.studentdirectory;

public class Student {
    private final String name;
    private final String course;
    private final String email;

    public Student(String name, String course, String email) {
        this.name = name;
        this.course = course;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getEmail() {
        return email;
    }
}