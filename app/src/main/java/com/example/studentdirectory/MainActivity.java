package com.example.studentdirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final List<Student> allStudents = new ArrayList<>();
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSampleStudents();

        RecyclerView recyclerView = findViewById(R.id.studentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StudentAdapter(allStudents, student ->
                new AlertDialog.Builder(this)
                        .setTitle(student.getName())
                        .setMessage(
                                "Course: " + student.getCourse()
                                        + "\nEmail: " + student.getEmail()
                        )
                        .setPositiveButton("Close", null)
                        .show()
        );
        recyclerView.setAdapter(adapter);

        TextInputEditText searchInput = findViewById(R.id.searchInput);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence text, int start, int count, int after) {
                // No action needed here.
            }

            @Override
            public void onTextChanged(
                    CharSequence text, int start, int before, int count) {
                filterStudents(text.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Filtering happens in onTextChanged.
            }
        });
    }

    private void addSampleStudents() {
        allStudents.add(new Student(
                "Aarav Mehta", "Computer Science", "aarav.mehta@example.com"));
        allStudents.add(new Student(
                "Ananya Rao", "Information Technology", "ananya.rao@example.com"));
        allStudents.add(new Student(
                "Ishaan Kapoor", "Data Science", "ishaan.kapoor@example.com"));
        allStudents.add(new Student(
                "Mira Shah", "Computer Science", "mira.shah@example.com"));
        allStudents.add(new Student(
                "Kabir Nair", "Electronics", "kabir.nair@example.com"));
        allStudents.add(new Student(
                "Sana Iyer", "Information Technology", "sana.iyer@example.com"));
        allStudents.add(new Student(
                "Vihaan Das", "Data Science", "vihaan.das@example.com"));
        allStudents.add(new Student(
                "Diya Menon", "Computer Science", "diya.menon@example.com"));
    }

    private void filterStudents(String query) {
        String search = query.trim().toLowerCase(Locale.ROOT);
        List<Student> filteredStudents = new ArrayList<>();

        for (Student student : allStudents) {
            boolean matches =
                    student.getName().toLowerCase(Locale.ROOT).contains(search)
                            || student.getCourse().toLowerCase(Locale.ROOT).contains(search)
                            || student.getEmail().toLowerCase(Locale.ROOT).contains(search);

            if (matches) {
                filteredStudents.add(student);
            }
        }

        adapter.updateStudents(filteredStudents);
    }
}