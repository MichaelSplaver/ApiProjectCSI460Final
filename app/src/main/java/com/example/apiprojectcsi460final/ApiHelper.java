package com.example.apiprojectcsi460final;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import javax.crypto.Cipher;

public class ApiHelper {

    public static ArrayList<Student> parseStudents(String jres) {
        ArrayList<Student> retList = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jres);
            JSONArray jarr = jobj.getJSONArray("students");
            for (int i=0; i < jarr.length(); i++){
                Student s = new Student();
                JSONObject sobj = jarr.getJSONObject(i);
                s.setId(sobj.getInt("id"));
                s.setFirstName(sobj.getString("first_name"));
                s.setLastName(sobj.getString("last_name"));
                s.setAddress(sobj.getString("address"));
                s.setRollNumber(sobj.getInt("roll_number"));
                s.setMobile(sobj.getString("mobile"));
                retList.add(s);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return retList;
    }

    public static String encodeStudent(Student student) {
        JsonObject jobj = new JsonObject();
        jobj.addProperty("first_name",student.getFirstName());
        jobj.addProperty("last_name",student.getLastName());
        jobj.addProperty("address",student.getAddress());
        jobj.addProperty("roll_number",student.getRollNumber());
        jobj.addProperty("mobile",student.getMobile());
        return jobj.toString();
    }

    public static class GetAllStudentsTask extends AsyncTask<Void,Void,String> {
        private View rootView;
        private Context ctx;
        private boolean isModifiable;
        public GetAllStudentsTask(View rootView, Context ctx, boolean isModifible){
            this.rootView=rootView;
            this.ctx=ctx;
            this.isModifiable=isModifible;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                StringBuilder res = new StringBuilder();
                URL url = new URL("https://f617-47-161-26-166.ngrok-free.app/api/basic/");
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    res.append(line);
                }
                return res.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                urlConnection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<Student> students = parseStudents(result);
            StudentAdapter studentAdapter;
            RecyclerView studentsRV;
            if (isModifiable) {
                studentAdapter = new StudentEditAdapter(students, ctx);
                studentsRV = rootView.findViewById(R.id.studenteditdeleteRV);
            }
            else {
                studentAdapter = new StudentAdapter(students, ctx);
                studentsRV = rootView.findViewById(R.id.apiAllViewRV);
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx, RecyclerView.VERTICAL, false);
            studentsRV.setLayoutManager(linearLayoutManager);

            studentsRV.setAdapter(studentAdapter);
        }
    }

    public static class DeleteStudentTask extends AsyncTask<Void,Void,Void> {

        private Context ctx;
        private int studentId;
        public DeleteStudentTask(Context ctx, int studentId){
            this.studentId=studentId;
            this.ctx=ctx;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                StringBuilder res = new StringBuilder();
                URL url = new URL("https://f617-47-161-26-166.ngrok-free.app/api/basic/" + studentId + "/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    res.append(line);
                }
                //check if success here??
                Void Void = null;
                return Void;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                urlConnection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(Void void1) {
            Toast.makeText(ctx, "Deleted the student!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ctx, EditDeleteStudentActivity.class);
            ctx.startActivity(i);
        }
    }

    public static class CreateNewStudentTask extends AsyncTask<Void,Void,String> {
        private Context ctx;
        private Student student;
        public CreateNewStudentTask(Context ctx, Student student){
            this.ctx=ctx;
            this.student=student;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                StringBuilder res = new StringBuilder();
                URL url = new URL("https://f617-47-161-26-166.ngrok-free.app/api/basic/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                try (DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream())) {
                    os.writeBytes(encodeStudent(student));
                    os.flush();
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    res.append(line);
                }
                return res.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                urlConnection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ctx, "The student has been added!", Toast.LENGTH_SHORT).show();
            //show the database activity with the new game to the user
            Intent intent = new Intent(ctx, ViewStudents.class);
            ctx.startActivity(intent);
        }
    }

    public static class UpdateStudentTask extends AsyncTask<Void,Void,String> {
        private Context ctx;
        private Student student;
        public UpdateStudentTask(Context ctx, Student student){
            this.ctx=ctx;
            this.student=student;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            try {
                StringBuilder res = new StringBuilder();
                URL url = new URL("https://f617-47-161-26-166.ngrok-free.app/api/basic/" + student.getId() + "/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PATCH");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                try (DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream())) {
                    os.writeBytes(encodeStudent(student));
                    os.flush();
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    res.append(line);
                }
                return res.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                urlConnection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ctx, "The student has been updated!", Toast.LENGTH_SHORT).show();
            //show the database activity with the new game to the user
            Intent intent = new Intent(ctx, ViewStudents.class);
            ctx.startActivity(intent);
        }
    }

}
