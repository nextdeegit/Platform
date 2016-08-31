package com.pies.platform.teachersActivity;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pies.platform.Login;
import com.pies.platform.R;
import com.pies.platform.admin.Create_Account;
import com.pies.platform.teachersActivity.model.Feedback_item;
import com.pies.platform.teachersActivity.model.teacher_data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String fb1 = "Was the tutor in class?";
    String fb2 = "Does the student understand the principle ?";
    String fb3 = "Can the student tackle the questions by themselves ?";
    String fb4 = "Was there an assignment given on this topic?";
    String fb5 = "Was there class work today ?";
    String fb6 = "how many? ";
    String fb7 = "Was there any issue with regards to the class?";
    String fb8 = "If yes what was it?";

    String rm1,rm2,rm3,rm4,rm5,rm6,rm7,rm8,name;
    RadioGroup radioGroup;
    EditText number,issue;
    Button submit;
 RelativeLayout relativeLayout;
    //relativeLayout.setVisibility(View.GONE);
    RelativeLayout relativeLayout1;
    // relativeLayout1.setVisibility(View.GONE);
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        relativeLayout = (RelativeLayout) findViewById(R.id.layer);
        relativeLayout.setVisibility(View.GONE);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.it);
         relativeLayout1.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(Feedback.this);
        progressDialog.setMessage("Sending.....");
        progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.assess));

        final String key = getIntent().getStringExtra("home_name");
        name = getIntent().getStringExtra("teacher_name");

        Toast.makeText(Feedback.this, key, Toast.LENGTH_SHORT).show();
        number = (EditText) findViewById(R.id.number);
        issue = (EditText) findViewById(R.id.issue);
        submit = (Button) findViewById(R.id.submit);


        radioGroup = (RadioGroup) findViewById(R.id.button);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                rm1  = (String) r.getText();

                Toast.makeText(Feedback.this, rm1, Toast.LENGTH_SHORT).show();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.button2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                rm2  = (String) r.getText();
                Toast.makeText(Feedback.this, rm2, Toast.LENGTH_SHORT).show();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.button3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                rm3  = (String) r.getText();
                Toast.makeText(Feedback.this, rm3, Toast.LENGTH_SHORT).show();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.button4);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                rm4 = (String) r.getText();
                Toast.makeText(Feedback.this, rm4, Toast.LENGTH_SHORT).show();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.button5);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                rm5 = (String) r.getText();
                if(rm5.equals("no")){
                    relativeLayout.setVisibility(View.GONE);
                }
                else{
                    relativeLayout.setVisibility(View.VISIBLE);
                }

                Toast.makeText(Feedback.this, rm5, Toast.LENGTH_SHORT).show();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.button7);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                rm7 = (String) r.getText();
                if(rm7.equals("no")){
                    relativeLayout1.setVisibility(View.GONE);
                }
                else {

                    relativeLayout1.setVisibility(View.VISIBLE);
                }
                Toast.makeText(Feedback.this, rm7, Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nHome = number.getText().toString();
                String isHome = issue.getText().toString();
                long msTime = System.currentTimeMillis();
                Date date = new Date(msTime);
                SimpleDateFormat sdf = new SimpleDateFormat(" MMMM d, y");
                SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a ");
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE ");
                String day = sdf2.format(date);
                String time =sdf1.format(date);
                String formattedDate = sdf.format(date);

                if(rm1 =="null"){
                    Toast.makeText(Feedback.this, "done", Toast.LENGTH_SHORT).show();
                }

               /* if(rm1=="" || rm2 =="" || rm3 =="" || rm4 =="" || rm5 ==""){
                    Toast.makeText(Feedback.this, "Incomplete information set!", Toast.LENGTH_SHORT).show();
                }
               else if(rm5 == "Yes" && nHome ==""){

                        number.setError("Add number of network");

                }
                else if(rm7 =="Yes" && isHome.isEmpty()){

                        issue.setError("What were the issues?");
                }
                else{
                    progressDialog.show();
                  //  createFeedback(day,time,formattedDate,name,key,fb1,fb2,fb3,fb4,fb5,fb6,fb7,fb8,rm1,rm2,rm3,rm4,rm5,nHome,rm7,isHome);
                }


*/
            }
        });



    }
    // [START write_fan_out]
    private void createFeedback(String day,String time,String date,String author,String homekey,final String fb1, String fb2, String fb3, String fb4, String fb5, String fb6,String fb7, String fb8,String rm1, String rm2, String rm3, String rm4, String rm5, String rm6,String rm7, String rm8) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("All-Feedbacks").push().getKey();
    Feedback_item feedback = new Feedback_item(day,time,date,author,fb1,rm1,fb2,rm2,fb3,rm3,fb4,rm4,fb5,rm5,fb6,rm6,fb7,rm7,fb8,rm8);
        Map<String, Object> postValues = feedback.toFeedback();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/All-Feedbacks/" + homekey  + "/" + key, postValues);
       /* childUpdates.put("/Teachers-Profile/" + uid + "/" + key, postValues);
        childUpdates.put("/auth-user/" + uid, postValues);
        childUpdates.put("/all-user/" + key, postValues);*/
        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Feedback.this, "Teacher not Added",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Feedback.this, "sucessfully added", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Feedback.this);
                    builder.setMessage("Feedback Sent")
                            .setTitle("Successfully")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   finish();

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

                progressDialog.hide();
                }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
