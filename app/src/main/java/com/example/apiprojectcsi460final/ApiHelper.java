package com.example.apiprojectcsi460final;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonObject;

//API Helper Class contains all the classes and methods used to interact with the API
public class ApiHelper {

    //method to parse out an array list of students from a json string result
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

    //method to encode a student into a json object to be passed in API requests.
    public static String encodeStudent(Student student) {
        JsonObject jobj = new JsonObject();
        jobj.addProperty("first_name",student.getFirstName());
        jobj.addProperty("last_name",student.getLastName());
        jobj.addProperty("address",student.getAddress());
        jobj.addProperty("roll_number",student.getRollNumber());
        jobj.addProperty("mobile",student.getMobile());
        return jobj.toString();
    }

    //class for retrieving a complete list of all students in the API
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
            //http connection code partially borrowed from https://blog.codavel.com/how-to-integrate-httpurlconnection
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
        //upon completion of get request populate the recycler views with the GET data. Include edit/delete icons if specified.
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
    //class for deletion of a student by student id
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
            //http connection code partially borrowed from https://blog.codavel.com/how-to-integrate-httpurlconnection
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
        //upon completion notify the user and return to the previous screen
        @Override
        protected void onPostExecute(Void void1) {
            Toast.makeText(ctx, "Deleted the student!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ctx, EditDeleteStudentActivity.class);
            ctx.startActivity(i);
        }
    }
    //class to create a new student with passed in student object
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
                //http connection code partially borrowed from https://blog.codavel.com/how-to-integrate-httpurlconnection
                URL url = new URL("https://f617-47-161-26-166.ngrok-free.app/api/basic/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                //data output stream code borrowed from https://www.geeksforgeeks.org/how-to-use-httpurlconnection-for-sending-http-post-requests-in-java/
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
        //upon completion notify the student was created and change activity to see all students
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ctx, "The student has been added!", Toast.LENGTH_SHORT).show();
            //show the database activity with the new game to the user
            Intent intent = new Intent(ctx, ViewStudents.class);
            ctx.startActivity(intent);
        }
    }

    //class that performs an update to an existing student by the student id
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
                //http connection code partially borrowed from https://blog.codavel.com/how-to-integrate-httpurlconnection
                //code modified to perform Patch functionality
                URL url = new URL("https://f617-47-161-26-166.ngrok-free.app/api/basic/" + student.getId() + "/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PATCH");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                //data output stream code borrowed from https://www.geeksforgeeks.org/how-to-use-httpurlconnection-for-sending-http-post-requests-in-java/
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

        //upon completion notify the user and change activity to see all students
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ctx, "The student has been updated!", Toast.LENGTH_SHORT).show();
            //show the database activity with the new game to the user
            Intent intent = new Intent(ctx, ViewStudents.class);
            ctx.startActivity(intent);
        }
    }

}
