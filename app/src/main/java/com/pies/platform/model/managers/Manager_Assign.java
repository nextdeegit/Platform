package com.pies.platform.model.managers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pies.platform.Login;
import com.pies.platform.R;
import com.pies.platform.model.managers.model.managers_data;
import com.pies.platform.teachersActivity.model.teacher_data;
import com.pies.platform.teachersActivity.model.teacher_item;
import com.pies.platform.viewHolder.ManagerListViewHolder;
import com.pies.platform.viewHolder.TeacherListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class Manager_Assign extends android.support.v4.app.Fragment {
    private List<teacher_item> movieList = new ArrayList<>();

    FirebaseAuth mFirebaseAuth;

    private FirebaseUser mFirebaseUser;
    private String teacherNam, teacherEmail;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView tName, tEmail;
    private String url;
    private String userId;
    DatabaseReference myRootref = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "PostListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<managers_data, ManagerListViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    ProgressDialog progressDialog;

    public Manager_Assign() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_managers_list, container, false);


        mFirebaseAuth = FirebaseAuth.getInstance();
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) root.findViewById(R.id.manager_list);
        mRecycler.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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

                }




            }};



        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<managers_data, ManagerListViewHolder>(managers_data.class, R.layout.manager_list,
                ManagerListViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final ManagerListViewHolder viewHolder, final managers_data model2, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);*/
                    }
                });
                viewHolder.bindToPost1(model2, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });


            }
        };
        mRecycler.setAdapter(mAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        // Query recentPostsQuery = databaseReference.child("Teachers-Added").child("SPUE1xO0JbTaXUqM80xPmszCUIK2");
        // [END recent_posts_query]
        ;
        return databaseReference.child("Managers-Added");
    }
}