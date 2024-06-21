package com.example.apiprojectcsi460final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{

    public ArrayList<Student> studentsArrayList;
    public Context context;

    public StudentAdapter(ArrayList<Student> studentsArrayList, Context context) {
        this.studentsArrayList = studentsArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //setting edit and delete buttons to be invisible on this adapter
        Student student = studentsArrayList.get(position);
        holder.studentidtext.setText("ID: " + student.getId());
        holder.studentnametext.setText(student.getFirstName() + " " + student.getLastName());
        holder.studentaddresstext.setText("Address: " + student.getAddress());
        holder.studentrollnumtext.setText("Roll Num: " + student.getRollNumber());
        holder.studentmobilenumtext.setText("Phone Num: " + student.getMobile());
        holder.deletebtn.setVisibility(View.INVISIBLE);
        holder.editbtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return studentsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentidtext, studentnametext, studentaddresstext, studentrollnumtext, studentmobilenumtext;
        public ImageView editbtn, deletebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentidtext = itemView.findViewById(R.id.studentidcard);
            studentnametext = itemView.findViewById(R.id.studentnamecard);
            studentaddresstext = itemView.findViewById(R.id.studentaddresscard);
            studentrollnumtext = itemView.findViewById(R.id.studentrollnumcard);
            studentmobilenumtext = itemView.findViewById(R.id.studentmobilenumcard);
            editbtn = itemView.findViewById(R.id.editimg);
            deletebtn = itemView.findViewById(R.id.closeimg);
        }
    }
}
