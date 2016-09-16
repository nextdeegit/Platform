package com.pies.platform.teachersActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
import com.pies.platform.admin.model.AdminAdapter;
import com.pies.platform.admin.model.Admin_Item;
import com.pies.platform.custom.DividerItemDecoration;
import com.pies.platform.model_users.Users;
import com.pies.platform.teachersActivity.model.Teacher_Adapter;
import com.pies.platform.teachersActivity.model.teacher_data;
import com.pies.platform.teachersActivity.model.teacher_item;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class Teachers_DashboardFragment extends Fragment {
    private List<teacher_item> movieList = new ArrayList<>();
    private RecyclerView recyclerView2;
    private Teacher_Adapter mAdapter;
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    FirebaseAuth mFirebaseAuth;
    private static final String TAG = "ManagerHome";
    private FirebaseUser mFirebaseUser;
    private String teacherNam, teacherEmail,name;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView tName, tEmail, t_index;
    private String url, home1, home2,home3;
    private String userId;
    DatabaseReference myRootref = FirebaseDatabase.getInstance().getReference();

    ProgressDialog progressDialog;
    public Teachers_DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teachers__dashboard, container, false);




        AdView mAdView = (AdView) root.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        tName = (TextView) root.findViewById(R.id.teacher_name);
        tEmail = (TextView) root.findViewById(R.id.teacher_email);
        t_index = (TextView) root.findViewById(R.id.t_index);

        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Setting up...");

        progressDialog.setIndeterminate(false);
        recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_teacher);
        mAdapter = new Teacher_Adapter(movieList);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), 2);
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setHasFixedSize(true);
        recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        setHasOptionsMenu(true);
        recyclerView2.setAdapter(mAdapter);
        recyclerView2.addOnItemTouchListener(new Admin_dashboard.RecyclerTouchListener(getActivity(), recyclerView2, new Admin_dashboard.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(position == 1){
                  Intent intent = new Intent(getActivity(), MyHome.class);
                    intent.putExtra("home1", home1);
                    intent.putExtra("home2", home2);
                    intent.putExtra("home3", home3);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    // myRootref.child("all-users").setValue("meeeeeee");
                }
                else if(position == 0){
                    Intent intent = new Intent(getActivity(), MyHome.class);
                    intent.putExtra("home1", home1);
                    intent.putExtra("home2", home2);
                    intent.putExtra("home3", home3);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
prepareMovieData();

        return root;
    }
    private void prepareMovieData() {
        teacher_item manager = new teacher_item("Make Objective", "12","goals to be achieved", getResources().getDrawable(R.drawable.ic_border_color_black_24dp));
        movieList.add(manager);

        manager = new teacher_item("Give Feedback","", "students performance per home", getResources().getDrawable(R.drawable.assign_icon));
        movieList.add(manager);


        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            mFirebaseUser = firebaseAuth.getCurrentUser();
            if (mFirebaseUser == null) {
                // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());
                startActivity(new Intent(getActivity(), Login.class));
            }
            else{

                if(mFirebaseUser.getEmail()!= null){
                    String n = mFirebaseUser.getEmail();
                    tEmail.setText(n);
                    userId = mFirebaseUser.getUid().toString();
                    userProfile(mFirebaseUser.getUid().toString());
                }

                Toast.makeText(getActivity(), mFirebaseUser.getUid().toString(), Toast.LENGTH_SHORT).show();

            }
        }};
    }


    public void userProfile(String uid) {
      progressDialog.show();
        mDatabase.child("Teachers-Info").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                teacher_data item = dataSnapshot.getValue(teacher_data.class);
                String url = item.getUserType();
                if(item.name != null){
                     name = item.name;
                    tName.setText(name);

                    String first = name.substring(0,1);
                   t_index.setText(first);
                }
                if(item.home1 != null){
                    home1 = item.home1.toString();
                }
                if(item.home2 != null){
                    home2 = item.home2.toString();
                }

                if(item.home3 != null){
                    home3 = item.home3.toString();
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.hide();
    }
    @Override
    public void onStart() {
        super.onStart();
/*serProfile(userId);
          //  userProfile(mFirebaseUser.getUid().toString());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser == null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());
                    startActivity(new Intent(getActivity(), Login.class));
                }
                else{

                    if(mFirebaseUser.getEmail()!= null){
                        String n = mFirebaseUser.getEmail();
                        tEmail.setText(n);
                        userId = mFirebaseUser.getUid().toString();
                        userProfile(mFirebaseUser.getUid().toString());
                    }

                    Toast.makeText(getActivity(), mFirebaseUser.getUid().toString(), Toast.LENGTH_SHORT).show();

                }
            }};*/

        mFirebaseAuth.addAuthStateListener(mAuthListener);


   }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
