package com.example.studentdirectory;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Database representation of a student.
 * Room stores each student as one row in the students table.
 */
@Entity(tableName = "students")
public class StudentEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String course;
    public String email;

    public StudentEntity(String name, String course, String email) {
        this.name = name;
        this.course = course;
        this.email = email;
    }
}
