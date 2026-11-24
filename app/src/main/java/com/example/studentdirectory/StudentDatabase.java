package com.example.studentdirectory;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Local Room database for the student directory.
 */
@Database(entities = {StudentEntity.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {

    private static volatile StudentDatabase INSTANCE;

    public abstract StudentDao studentDao();

    public static StudentDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (StudentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    StudentDatabase.class,
                                    "student_directory_database"
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
