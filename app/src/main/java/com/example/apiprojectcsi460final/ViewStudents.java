package com.example.apiprojectcsi460final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ViewStudents extends AppCompatActivity {

    private ArrayList<Student> videoGameModalArrayList;
    private StudentAdapter studentAdapter;
    private RecyclerView studentsRV;

    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        new ApiHelper.GetAllStudentsTask(findViewById(android.R.id.content).getRootView(),this, false).execute();

        backBtn = findViewById(R.id.backbtn1);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}