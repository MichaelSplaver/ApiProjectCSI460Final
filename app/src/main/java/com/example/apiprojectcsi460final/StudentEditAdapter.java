package com.example.apiprojectcsi460final;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class StudentEditAdapter extends StudentAdapter{

    public StudentEditAdapter(ArrayList<Student> studentsArrayList, Context context) {
        super(studentsArrayList, context);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        //overwriting the base VideogameAdapter to make the edit/delete buttons visible
        Student student = studentsArrayList.get(position);
        holder.studentidtext.setText("ID: " + student.getId());
        holder.studentnametext.setText(student.getFirstName() + " " + student.getLastName());
        holder.studentrollnumtext.setText("Address: " + student.getAddress());
        holder.studentrollnumtext.setText("Roll Num: " + student.getRollNumber());
        holder.studentmobilenumtext.setText("Phone Num: " + student.getMobile());
        holder.deletebtn.setVisibility(View.VISIBLE);
        holder.editbtn.setVisibility(View.VISIBLE);

        //adding delete functionality to the delete button
        holder.deletebtn.setOnClickListener(view -> {
            new ApiHelper.DeleteStudentTask(context,student.getId()).execute();
            Toast.makeText(context, "Deleted the game!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, EditDeleteStudentActivity.class);
            context.startActivity(i);
        });

        //passing the relevant info in extras to the ModifyGame Activity
        holder.editbtn.setOnClickListener(view -> {
            Intent i = new Intent(context, ModifyStudentActivity.class);

            i.putExtra("id", student.getId());
            i.putExtra("firstname", student.getFirstName());
            i.putExtra("lastname", student.getLastName());
            i.putExtra("address", student.getAddress());
            i.putExtra("rollnum", student.getRollNumber());
            i.putExtra("mobile", student.getMobile());

            context.startActivity(i);
        });
    }

}
