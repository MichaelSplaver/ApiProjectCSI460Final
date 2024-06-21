package com.example.apiprojectcsi460final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyStudentActivity extends AppCompatActivity {

    private EditText studentIDEdt, studentFirstNameEdt, studentLastNameEdt, studentAddressEdt, studentRollNumEdt, studentMobileEdt;
    private Button updateStudentBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_student);

        studentIDEdt = findViewById(R.id.editstudentid);
        studentFirstNameEdt = findViewById(R.id.editstudentfirstname);
        studentLastNameEdt = findViewById(R.id.editstudentlastname);
        studentAddressEdt = findViewById(R.id.editstudentaddress);
        studentRollNumEdt = findViewById(R.id.editstudentrollnum);
        studentMobileEdt = findViewById(R.id.editstudentmobile);

        updateStudentBtn = findViewById(R.id.modifynewstudentbtn);
        backBtn = findViewById(R.id.backbtn4);

        studentIDEdt.setText("ID: " + getIntent().getIntExtra("id",-1));
        studentFirstNameEdt.setText(getIntent().getStringExtra("firstname"));
        studentLastNameEdt.setText(getIntent().getStringExtra("lastname"));
        studentAddressEdt.setText(getIntent().getStringExtra("address"));
        studentRollNumEdt.setText(String.valueOf(getIntent().getIntExtra("rollnum",-1)));
        studentMobileEdt.setText(getIntent().getStringExtra("mobile"));

        updateStudentBtn.setOnClickListener(view -> {
            Student student = new Student();
            //getting the inputted values and validating them to be acceptable before continuing
            int id = getIntent().getIntExtra("id",-1);
            String firstname = studentFirstNameEdt.getText().toString();
            String lastname = studentLastNameEdt.getText().toString();
            String address = studentAddressEdt.getText().toString();
            String mobile = studentMobileEdt.getText().toString();
            if (studentRollNumEdt.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }
            int studentRollNum = Integer.parseInt(studentRollNumEdt.getText().toString());
            if (studentRollNum > 100000 || studentRollNum < 0) {
                Toast.makeText(this, "Please enter a roll number between 0-100000", Toast.LENGTH_SHORT).show();
                return;
            }
            if (firstname.isEmpty() || lastname.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
                Toast.makeText(this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }
            student.setId(id);
            student.setFirstName(firstname);
            student.setLastName(lastname);
            student.setAddress(address);
            student.setRollNumber(studentRollNum);
            student.setMobile(mobile);

            new ApiHelper.UpdateStudentTask(this, student).execute();
        });

        backBtn.setOnClickListener(view -> {
            //back button moves us to the previous activity
            Intent i = new Intent(this, EditDeleteStudentActivity.class);
            startActivity(i);
        });
    }
}