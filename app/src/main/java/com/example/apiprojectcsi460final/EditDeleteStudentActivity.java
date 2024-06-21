package com.example.apiprojectcsi460final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class EditDeleteStudentActivity extends AppCompatActivity {

    private ArrayList<Student> studentsArrayList;
    private StudentEditAdapter studentEditAdapter;
    private RecyclerView studenteditdeleteRV;

    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_student);

        new ApiHelper.GetAllStudentsTask(findViewById(android.R.id.content).getRootView(),this, true).execute();

        backBtn = findViewById(R.id.backbtn3);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}