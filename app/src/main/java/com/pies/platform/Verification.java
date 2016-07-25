package com.pies.platform;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pies.platform.admin.Admin_dashboard;
import com.pies.platform.custom.CircleProgressBar;
import com.pies.platform.custom.ColorPickerDialog;
import com.pies.platform.custom.ColorPickerSwatch;
import com.pies.platform.model_users.Users;
import com.pies.platform.teachersActivity.Teachers_Dashboard;

public class Verification extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    // [START declare_database_ref]
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseUser mFirebaseUser;
    private  ProgressDialog progressDialog;
    private DonutProgress progressBar;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    progressBar = (DonutProgress) findViewById(R.id.donut_progress);




       // progressBar.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(Verification.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting Ready...");
        progressDialog.setCancelable(false);


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [START auth_state_listener]


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());
                     userID =  mFirebaseUser.getUid();
                    checkUser(userID);
                    Toast.makeText(Verification.this,userID, Toast.LENGTH_SHORT).show();
                    // createAdmin("","Paul","edakndk@gmail.com", "cagjagkcjgak","Admin");
                }else{

                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }};
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    public void checkUser(String uid) {
       progressBar.setVisibility(View.VISIBLE);

        mDatabase.child("auth-user").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users item = dataSnapshot.getValue(Users.class);
                progressBar.setProgress(progressBar.getProgress() + 35);
                String url = item.getUserType();
                if (url.equals("Manager")) {
                    progressBar.setProgress(progressBar.getProgress() + 35);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                  progressBar.setVisibility(View.INVISIBLE);
                }else if(url.equals("Admin")){
                    progressBar.setProgress(progressBar.getProgress() + 35);
                    startActivity(new Intent(getApplicationContext(), Admin_dashboard.class));
                    progressBar.setVisibility(View.INVISIBLE);
                    finish();
                }
                else if(url.equals("Teacher")){
                    progressBar.setProgress(progressBar.getProgress() + 35);
                    startActivity(new Intent(getApplicationContext(), Teachers_Dashboard.class));
                    progressBar.setVisibility(View.INVISIBLE);
                    finish();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
