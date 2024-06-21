package com.example.apiprojectcsi460final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button modifyDeleteStudentBtn, addNewStudentBtn,viewAllStudentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up the main activity buttons to take us to the right respective activities
        modifyDeleteStudentBtn = findViewById(R.id.modifydeletestudentbtn);
        addNewStudentBtn = findViewById(R.id.addstudentbtn);
        viewAllStudentsBtn = findViewById(R.id.viewstudentsbtn);

        viewAllStudentsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ViewStudents.class);
            startActivity(intent);
        });

        addNewStudentBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateStudentActivity.class);
            startActivity(intent);
        });

        modifyDeleteStudentBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditDeleteStudentActivity.class);
            startActivity(intent);
        });


    }
}