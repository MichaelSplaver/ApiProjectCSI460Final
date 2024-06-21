package com.example.apiprojectcsi460final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateStudentActivity extends AppCompatActivity {

    private EditText inputStudentFirstName, inputStudentLastName, inputStudentAddress, inputStudentRollNum, inputStudentMobile;
    private Button createNewStudentBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        inputStudentFirstName = findViewById(R.id.inputstudentfirstname);
        inputStudentLastName = findViewById(R.id.inputstudentlastname);
        inputStudentAddress = findViewById(R.id.inputstudentaddress);
        inputStudentRollNum = findViewById(R.id.inputstudentrollnum);
        inputStudentMobile = findViewById(R.id.inputstudentmobile);

        createNewStudentBtn = findViewById(R.id.addnewstudentbtn);
        backBtn = findViewById(R.id.backbtn2);

        createNewStudentBtn.setOnClickListener(view -> {
            Student newStudent = new Student();
            //getting the inputted values and validating them to be acceptable before continuing
            String firstname = inputStudentFirstName.getText().toString();
            String lastname = inputStudentLastName.getText().toString();
            String address = inputStudentAddress.getText().toString();
            String mobile = inputStudentMobile.getText().toString();
            if (inputStudentRollNum.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }
            int studentRollNum = Integer.parseInt(inputStudentRollNum.getText().toString());
            if (studentRollNum > 100000 || studentRollNum < 0) {
                Toast.makeText(this, "Please enter a roll number between 0-100000", Toast.LENGTH_SHORT).show();
                return;
            }
            if (firstname.isEmpty() || lastname.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
                Toast.makeText(this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }
            newStudent.setFirstName(firstname);
            newStudent.setLastName(lastname);
            newStudent.setAddress(address);
            newStudent.setRollNumber(Integer.parseInt(inputStudentRollNum.getText().toString()));
            newStudent.setMobile(mobile);

            new ApiHelper.CreateNewStudentTask(this, newStudent).execute();
        });

        backBtn.setOnClickListener(view -> {
            //back button simply returns user to the main activity
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }
}