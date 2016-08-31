package com.pies.platform.managersActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pies.platform.Login;
import com.pies.platform.R;
import com.pies.platform.admin.Admin_dashboard;
import com.pies.platform.admin.Create_Account;
import com.pies.platform.admin.MapsActivity;
import com.pies.platform.admin.model.AdminAdapter;
import com.pies.platform.admin.model.Admin_Item;
import com.pies.platform.custom.DividerItemDecoration;
import com.pies.platform.model.managers.ManagersAdapter;
import com.pies.platform.model.managers.ManagersItem;
import com.pies.platform.teachersActivity.Assignment;
import com.pies.platform.teachersActivity.model.teacher_data;

import java.util.ArrayList;
import java.util.List;

public class Manager_Dashboard extends AppCompatActivity {
    private List<ManagersItem> movieList = new ArrayList<>();
    private RecyclerView recyclerView2;
    private ManagersAdapter mAdapter;
    private TextView tName, tEmail, t_index, userType;
    private FirebaseUser mFirebaseUser;
    private String teacherNam, teacherEmail, userid;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabase;

    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAuth = FirebaseAuth.getInstance();

        tName = (TextView) findViewById(R.id.manager_name);
        tEmail = (TextView)findViewById(R.id.manager_email);
        t_index = (TextView) findViewById(R.id.textView2);
        userType = (TextView) findViewById(R.id.manager_type);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_manager);
        mAdapter = new ManagersAdapter(movieList);
       // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Manager_Dashboard.this, 1);
        recyclerView2.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setHasFixedSize(true);
        recyclerView2.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView2.setAdapter(mAdapter);
        recyclerView2.addOnItemTouchListener(new Admin_dashboard.RecyclerTouchListener(getApplicationContext(), recyclerView2, new Admin_dashboard.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(position == 0){
                    startActivity(new Intent(getApplicationContext(),Teachers.class));
                    // myRootref.child("all-users").setValue("meeeeeee");
                }
                else if(position == 1){
                    startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                }
                else if(position == 2){
                    startActivity(new Intent(getApplicationContext(),AssignHome.class));
                }
                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();

    }

    @Override
    protected void onStart() {
        super.onStart();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser == null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                else{

                    if(mFirebaseUser.getEmail()!= null){
                        String n = mFirebaseUser.getEmail();
                        tEmail.setText(n);
                         userid = mFirebaseUser.getUid().toString();
                        userProfile(userid);
                    }

                    //  Toast.makeText(getActivity(), mFirebaseUser.getUid().toString(), Toast.LENGTH_SHORT).show();

                }
            }};
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void userProfile(String uid) {
        //  progressDialog.show();
        mDatabase.child("Managers-Profile").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                teacher_data item = dataSnapshot.getValue(teacher_data.class);


                if(item.getUserType() != null){
                    String url = item.getUserType();
                    userType.setText(url);
                }


                if(item.name != null){
                    String name = item.name;
                    tName.setText(name);

                    String first = name.substring(0,1);
                    t_index.setText(first);
                }

               /* if(item.home1 != null){
                    home1 = item.home1.toString();
                }
                if(item.home2 != null){
                    home2 = item.home2.toString();
                }

                if(item.home3 != null){
                    home3 = item.home3.toString();
                }*/




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void prepareMovieData() {
       ManagersItem manager = new ManagersItem("Assessment & Remark", "12","Performance should be scored in %", getResources().getDrawable(R.drawable.ic_assessment_black_24dp));
        movieList.add(manager);

        manager = new ManagersItem("New Homes","", "registration of new homes", getResources().getDrawable(R.drawable.home_icon));
        movieList.add(manager);

        manager = new ManagersItem("Assign Homes","", "Allocated homes to personnel ", getResources().getDrawable(R.drawable.assign_icon));
        movieList.add(manager);

        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
