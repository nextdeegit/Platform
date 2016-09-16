package com.pies.platform;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pies.platform.DefaultModel.NotificationItem;
import com.pies.platform.DefaultModel.Teacher_requestItem;

import java.util.HashMap;
import java.util.Map;

public class Teacher_Request extends AppCompatActivity {
    ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private EditText name,email,phone,city,subjects,comment;
    Button request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            //database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Sending Request.....");


        name = (EditText) findViewById(R.id.input_request_name);
        email = (EditText) findViewById(R.id.input_request_email);
        phone = (EditText) findViewById(R.id.input_request_phone);
        city = (EditText) findViewById(R.id.input_request_City);
        subjects = (EditText) findViewById(R.id.input_request_Subjects);
        comment = (EditText) findViewById(R.id.input_request_Comment);
        request = (Button) findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam = name.getText().toString();
                String emal = email.getText().toString();
                String phne = phone.getText().toString();
                String cty = city.getText().toString();
                String sub = subjects.getText().toString();
                String comments = comment.getText().toString();

                if(nam.isEmpty() || emal.isEmpty() || phne.isEmpty() || cty.isEmpty() || sub.isEmpty() || comments.isEmpty()){
                    Toast.makeText(Teacher_Request.this, "Please complete the fill", Toast.LENGTH_SHORT).show();
                }
                else {
                    createRequest(nam,emal,phne,cty,sub,comments);
                }

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    // [START write_fan_out]
    private void createRequest(String pName, String pEmail, String pNumber, String pCity, String pSubjects, String pComment) {
        progressDialog.show();
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("Teacher-Request").push().getKey();
       Teacher_requestItem notify = new Teacher_requestItem(pName,pEmail,pNumber,pCity,pSubjects,pComment);
        Map<String, Object> postValues = notify.toRequest();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/Teacher-Request/" + key, postValues);


        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Teacher_Request.this, "request not sent",
                            Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Teacher_Request.this);
                    builder.setMessage("Request Sent")
                            .setTitle("Successfully")
                            .setCancelable(true)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),Home.class));


                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
                progressDialog.hide();

            }
        });
    }
}
