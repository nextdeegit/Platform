package com.pies.platform.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pies.platform.Feedbackdetails;
import com.pies.platform.R;
import com.pies.platform.admin.model.Add_Home_item;
import com.pies.platform.managersActivity.Objandfeedback;
import com.pies.platform.managersActivity.Teachers;
import com.pies.platform.teachersActivity.model.Feedback_item;
import com.pies.platform.viewHolder.FeedbackListViewholder;
import com.pies.platform.viewHolder.FeedbackViewHolder;
import com.pies.platform.viewHolder.ManagerHomeList;

/**
 * A placeholder fragment containing a simple view.
 */
public class Feedback_FeedsFragment extends Fragment {
    private FirebaseRecyclerAdapter<Feedback_item,FeedbackListViewholder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    ProgressBar progressBar;
    String name;
    TextView status;

    @SuppressLint("ValidFragment")
    public  Feedback_FeedsFragment()

    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feedback__feeds, container, false);


        //  pd = (ProgressBar) root.findViewById(R.id.progress);
        mRecycler = (RecyclerView) root.findViewById(R.id.feed_list);
        mRecycler.setHasFixedSize(true);


        // Set up Layout Manager, reverse layout

        progressBar = (ProgressBar) root.findViewById(R.id.move);
        status = (TextView) root.findViewById(R.id.status);
        Intent intent = getActivity().getIntent();
        //name = intent.getStringExtra("teacher_name");

        progressBar.setVisibility(View.VISIBLE);






        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // [START create_database_reference]


        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

            Toast.makeText(getActivity(), Objandfeedback.name, Toast.LENGTH_SHORT).show();



        new Update().execute();



        // listView not empty


        progressBar.setVisibility(View.INVISIBLE);

    }

    public Query getQuery(DatabaseReference databaseReference, String name) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        // Query recentPostsQuery = databaseReference.child("Teachers-Added").child("SPUE1xO0JbTaXUqM80xPmszCUIK2");
        // [END recent_posts_query]
        ;
        return databaseReference.child("All-Feedbacks").child(name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    public void onPostUploaded(final String error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                    Toast.makeText(getActivity(), "notin founded", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private class Update extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


    if(!Objandfeedback.name.isEmpty()){
        Query postsQuery = getQuery(mDatabase,Objandfeedback.name);
        mAdapter = new FirebaseRecyclerAdapter<Feedback_item, FeedbackListViewholder>(Feedback_item.class, R.layout.feedback_list,
                FeedbackListViewholder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final FeedbackListViewholder viewHolder, final Feedback_item model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                //  postKey = postRef.getKey();


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity


                        Intent intent = new Intent(getActivity(), Feedbackdetails.class);
                        intent.putExtra("author", model.getAuthor());
                        intent.putExtra("time",model.getTime());
                        intent.putExtra("f1", model.getFb1());
                        intent.putExtra("f2", model.getFb2());
                        intent.putExtra("f3", model.getFb3());
                        intent.putExtra("f4", model.getFb4());
                        intent.putExtra("f5", model.getFb5());
                        intent.putExtra("f6", model.getFb6());
                        intent.putExtra("f7", model.getFb7());
                        intent.putExtra("f8", model.getFb8());
                        intent.putExtra("rm1", model.getRm1());
                        intent.putExtra("rm2", model.getRm2());
                        intent.putExtra("rm3", model.getRm3());
                        intent.putExtra("rm4", model.getRm4());
                        intent.putExtra("rm5", model.getRm5());
                        intent.putExtra("rm6", model.getRm6());
                        intent.putExtra("rm7", model.getRm7());
                        intent.putExtra("rm8", model.getRm8());
                        intent.putExtra("feed_key", postRef.getKey().toString());

                        // intent.putExtra("uid", userId);

                        startActivity(intent);
                        // Toast.makeText(Teachers.this, model.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });


            }
        };


    }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


                mRecycler.setAdapter(mAdapter);




        }
    }
}
