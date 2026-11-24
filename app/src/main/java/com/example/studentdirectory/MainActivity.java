package com.example.studentdirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final List<Student> allStudents = new ArrayList<>();
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    private StudentAdapter adapter;
    private StudentDatabase database;
    private TextInputEditText searchInput;
    private TextView countLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = StudentDatabase.getInstance(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.studentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        countLabel = findViewById(R.id.countLabel);
        searchInput = findViewById(R.id.searchInput);

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

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence text, int start, int count, int after) {
                // No action needed.
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

        FloatingActionButton addButton = findViewById(R.id.addStudentButton);
        addButton.setOnClickListener(view -> showAddStudentDialog());

        // Initial database read and one-time sample insertion run off the UI thread.
        loadStudentsFromDatabase();
    }

    /**
     * Loads saved records. Sample students are inserted only the first time
     * the app runs, so later launches use the data already stored in Room.
     */
    private void loadStudentsFromDatabase() {
        databaseExecutor.execute(() -> {
            StudentDao dao = database.studentDao();

            if (dao.getStudentCount() == 0) {
                dao.insertStudents(createSampleStudents());
            }

            List<StudentEntity> savedStudents = dao.getAllStudents();
            List<Student> displayStudents = new ArrayList<>();

            for (StudentEntity entity : savedStudents) {
                displayStudents.add(
                        new Student(entity.name, entity.course, entity.email)
                );
            }

            runOnUiThread(() -> {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                allStudents.clear();
                allStudents.addAll(displayStudents);
                filterStudents(searchInput.getText() == null
                        ? "" : searchInput.getText().toString());
            });
        });
    }

    private List<StudentEntity> createSampleStudents() {
        List<StudentEntity> samples = new ArrayList<>();
        samples.add(new StudentEntity(
                "Aarav Mehta", "Computer Science", "aarav.mehta@example.com"));
        samples.add(new StudentEntity(
                "Ananya Rao", "Information Technology", "ananya.rao@example.com"));
        samples.add(new StudentEntity(
                "Ishaan Kapoor", "Data Science", "ishaan.kapoor@example.com"));
        samples.add(new StudentEntity(
                "Mira Shah", "Computer Science", "mira.shah@example.com"));
        samples.add(new StudentEntity(
                "Kabir Nair", "Electronics", "kabir.nair@example.com"));
        samples.add(new StudentEntity(
                "Sana Iyer", "Information Technology", "sana.iyer@example.com"));
        samples.add(new StudentEntity(
                "Vihaan Das", "Data Science", "vihaan.das@example.com"));
        samples.add(new StudentEntity(
                "Diya Menon", "Computer Science", "diya.menon@example.com"));
        return samples;
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
        countLabel.setText(filteredStudents.size() + " STUDENTS");
    }

    /**
     * Collects student details on the UI thread, then inserts the record
     * asynchronously so database work never blocks screen interaction.
     */
    private void showAddStudentDialog() {
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        int horizontalPadding = dpToPx(24);
        form.setPadding(horizontalPadding, dpToPx(8),
                horizontalPadding, 0);

        EditText nameInput = createDialogInput("Student name");
        EditText courseInput = createDialogInput("Course / department");
        EditText emailInput = createDialogInput("Email address");

        form.addView(nameInput);
        form.addView(courseInput);
        form.addView(emailInput);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a student")
                .setView(form)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save student", null)
                .create();

        dialog.setOnShowListener(ignored -> dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(view -> {
                    String name = nameInput.getText().toString().trim();
                    String course = courseInput.getText().toString().trim();
                    String email = emailInput.getText().toString().trim();

                    if (name.isEmpty() || course.isEmpty() || email.isEmpty()) {
                        Toast.makeText(this,
                                "Please complete all three fields.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    dialog.dismiss();
                    saveStudent(name, course, email);
                }));

        dialog.show();
    }

    private EditText createDialogInput(String hint) {
        EditText input = new EditText(this);
        input.setHint(hint);
        input.setSingleLine(true);
        input.setTextColor(getColor(R.color.ivory));
        input.setHintTextColor(getColor(R.color.muted_ivory));
        input.setPadding(0, dpToPx(12), 0, dpToPx(12));
        return input;
    }

    private void saveStudent(String name, String course, String email) {
        databaseExecutor.execute(() -> {
            database.studentDao().insertStudent(
                    new StudentEntity(name, course, email)
            );
            List<StudentEntity> savedStudents =
                    database.studentDao().getAllStudents();
            List<Student> displayStudents = new ArrayList<>();

            for (StudentEntity entity : savedStudents) {
                displayStudents.add(
                        new Student(entity.name, entity.course, entity.email)
                );
            }

            runOnUiThread(() -> {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                allStudents.clear();
                allStudents.addAll(displayStudents);
                filterStudents(searchInput.getText() == null
                        ? "" : searchInput.getText().toString());
                Toast.makeText(this, "Student saved successfully.",
                        Toast.LENGTH_SHORT).show();
            });
        });
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseExecutor.shutdown();
    }
}
