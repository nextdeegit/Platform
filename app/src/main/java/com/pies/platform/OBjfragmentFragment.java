package com.pies.platform;

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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pies.platform.managersActivity.Objandfeedback;
import com.pies.platform.teachersActivity.model.Feedback_item;
import com.pies.platform.teachersActivity.model.ObjItem;
import com.pies.platform.viewHolder.FeedbackListViewholder;
import com.pies.platform.viewHolder.ObjectivesViewHolder;

/**
 * A placeholder fragment containing a simple view.
 */
public class OBjfragmentFragment extends Fragment {
    private FirebaseRecyclerAdapter<ObjItem,ObjectivesViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    public OBjfragmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View root =  inflater.inflate(R.layout.fragment_objfragment, container, false);
        mRecycler = (RecyclerView) root.findViewById(R.id.obj_list);
        mRecycler.setHasFixedSize(true);
        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);
        // [END create_database_reference]
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        new Update().execute();
    }
    public Query getQuery(DatabaseReference databaseReference, String name) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        // Query recentPostsQuery = databaseReference.child("Teachers-Added").child("SPUE1xO0JbTaXUqM80xPmszCUIK2");
        // [END recent_posts_query]
        ;
        return databaseReference.child("all-Objectives").child(name);
    }
    private class Update extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            if(!Objandfeedback.name.isEmpty()){
                Query postsQuery = getQuery(mDatabase,Objandfeedback.name);
                mAdapter = new FirebaseRecyclerAdapter<ObjItem, ObjectivesViewHolder>(ObjItem.class, R.layout.obj_list,
                        ObjectivesViewHolder.class, postsQuery) {
                    @Override
                    protected void populateViewHolder(final ObjectivesViewHolder viewHolder, final ObjItem model, final int position) {
                        final DatabaseReference postRef = getRef(position);

                        // Set click listener for the whole post view
                        //  postKey = postRef.getKey();


                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Launch PostDetailActivity


                                Intent intent = new Intent(getActivity(),
                                        Objectives_Details.class);
                                intent.putExtra("author", model.getAuthor());
                                intent.putExtra("time",model.getSent_time());
                                intent.putExtra("subject", model.getSubject());
                                intent.putExtra("topic", model.getTopic());
                                intent.putExtra("objec", model.getObjective());

                                intent.putExtra("obj_key", postRef.getKey().toString());


                                //intent.putExtra("uid", userId);

                                startActivity(intent);

                                Toast.makeText(getActivity(), model.getObjective(), Toast.LENGTH_SHORT).show();
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
