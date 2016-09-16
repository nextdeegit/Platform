package com.pies.platform.managersActivity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pies.platform.R;
import com.pies.platform.Works_Detail;
import com.pies.platform.admin.Feedback_FeedsFragment;
import com.pies.platform.admin.model.Add_Home_item;
import com.pies.platform.teachersActivity.Create_Obj;
import com.pies.platform.teachersActivity.model.teacher_data;
import com.pies.platform.viewHolder.ManagerHomeList;
import com.pies.platform.viewHolder.TeacherListViewHolder;

import java.util.ArrayList;

public class Teachers extends AppCompatActivity {
    private FirebaseRecyclerAdapter<Add_Home_item,ManagerHomeList> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    private ProgressBar pd;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String[] has = {"home1","home 2","home3"};
    private DatabaseReference mPostReference;
    ArrayList<String> homeData = new ArrayList<String>();
    ProgressBar progressBar;
    Dialog dialog;
    ArrayAdapter<String> adapter;
    ListView list;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_stat_name)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!")
                .setColor(getResources().getColor(R.color.colorPrimary));
                mBuilder.build();
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dialog = new Dialog(Teachers.this);
        dialog.setContentView(R.layout.popup);
         progressBar = (ProgressBar) dialog.findViewById(R.id.prog);
         list = (ListView)dialog.findViewById(R.id.listExample);
        dialog.setTitle("Heart attack and shock");
        dialog.setCancelable(true);




        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        new updater().execute();
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // [START create_database_reference]

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // [END create_database_reference]
      //  pd = (ProgressBar) root.findViewById(R.id.progress);
        mRecycler = (RecyclerView) findViewById(R.id.teacher_list1);
        mRecycler.setHasFixedSize(true);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getApplicationContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mAdapter != null){
                    mRecycler.setAdapter(mAdapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                //up();
            }});

    }


    private  void up(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                new updater().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void updateList(){
        mSwipeRefreshLayout.setRefreshing(true);
        // Set up FirebaseRecyclerAdapter with the Query

        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Add_Home_item,ManagerHomeList>(Add_Home_item.class, R.layout.teachers_list,
                ManagerHomeList.class, postsQuery) {
            @Override
            protected void populateViewHolder(final ManagerHomeList viewHolder, final Add_Home_item model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
              //  postKey = postRef.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        String n = model.getName();
                        Feedback_FeedsFragment frag = new Feedback_FeedsFragment();
                     bundle = new Bundle();
                        bundle.putString("teachername", model.getName());
                      // frag.setArguments(bundle);

                        Intent intent = new Intent(getApplicationContext(), Objandfeedback.class);
                        intent.putExtras(bundle);
                       // intent.putExtra("uid", userId);

                        startActivity(intent);


                     //   Toast.makeText(Teachers.this, model.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                try {

                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                } catch (Exception e) {

                    e.printStackTrace();
                }


            }
        };

    }


    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        // Query recentPostsQuery = databaseReference.child("Teachers-Added").child("SPUE1xO0JbTaXUqM80xPmszCUIK2");
        // [END recent_posts_query]
        ;
        return databaseReference.child("Homes-Added");
    }
  /*  private  void updateHome(String uid){
        dialog.show();
        progressBar.setVisibility(View.VISIBLE);
        Query queryRef = getQuery(mDatabase);
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Teachers-Info").child(uid);

        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
               *//* if(dataSnapshot != null){
                    String h = dataSnapshot.child("name").getValue().toString();
                    //Add_Home_item item = dataSnapshot.child("name").getValue(Add_Home_item.class);
                    homeData.add(h);
                }*//*
                   // String h = dataSnapshot.child("name").getValue().toString();
                    //Toast.makeText(Works_Detail.this, h, Toast.LENGTH_SHORT).show();

                  teacher_data item = dataSnapshot.getValue(teacher_data.class);
                if(item.home1 != null){
                    homeData.add(item.home1.toString());
                    homeData.add(item.home2.toString());
                    homeData.add(item.home3.toString());
                }




                // homeData.add(item.name);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                Toast.makeText(Teachers.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
        progressBar.setVisibility(View.INVISIBLE);
      adapter = new ArrayAdapter<String> (Teachers.this,android.R.layout.simple_list_item_1,homeData);
        list.setAdapter(adapter);



       *//* mPostReference.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter1.add((String)dataSnapshot.child("name").getValue());
            }
            public void onChildRemoved(DataSnapshot dataSnapshot) {
               // adapter.remove((String)dataSnapshot.child("text").getValue());
            }
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            public void onCancelled(FirebaseError firebaseError) { }
        });

*//*





    }*/
    private class updater extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            updateList();
            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecycler.setAdapter(mAdapter);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
