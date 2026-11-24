package com.example.studentdirectory;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Defines the database operations used by StudentDirectory.
 */
@Dao
public interface StudentDao {

    @Query("SELECT * FROM students ORDER BY name COLLATE NOCASE ASC")
    List<StudentEntity> getAllStudents();

    @Query("SELECT COUNT(*) FROM students")
    int getStudentCount();

    @Insert
    long insertStudent(StudentEntity student);

    @Insert
    void insertStudents(List<StudentEntity> students);
}
