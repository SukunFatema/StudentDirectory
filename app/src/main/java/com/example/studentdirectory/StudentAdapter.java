package com.example.studentdirectory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    public interface OnStudentClickListener {
        void onStudentClick(Student student);
    }

    private final List<Student> students = new ArrayList<>();
    private final OnStudentClickListener clickListener;

    public StudentAdapter(List<Student> students, OnStudentClickListener clickListener) {
        this.students.addAll(students);
        this.clickListener = clickListener;
    }

    public void updateStudents(List<Student> newStudents) {
        students.clear();
        students.addAll(newStudents);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);

        holder.name.setText(student.getName());
        holder.course.setText(student.getCourse());
        holder.email.setText(student.getEmail());

        holder.itemView.setOnClickListener(
                view -> clickListener.onStudentClick(student)
        );
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView course;
        private final TextView email;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            course = itemView.findViewById(R.id.studentCourse);
            email = itemView.findViewById(R.id.studentEmail);
        }
    }
}