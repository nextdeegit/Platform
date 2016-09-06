package com.pies.platform;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import  com.pies.platform.model.managers.Comment;
import com.pies.platform.model.managers.model.managers_data;


import java.util.ArrayList;
import java.util.List;

public class Feedbackdetails extends AppCompatActivity {
    public TextView fb1,fb2,fb3,fb4,fb5,fb6,fb7,fb8,rm1,rm2,rm3,rm4,rm5,rm6,rm7,rm8,author,time;
    private DatabaseReference mCommentsReference,mCommentsReference1;
    private ValueEventListener mPostListener;
    private EditText mCommentField;
    private FloatingActionButton mCommentButton;
    private RecyclerView mCommentsRecycler;
    private CommentAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabcomment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                postComment();


            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String feedkey = getIntent().getStringExtra("feed_key");
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("feedback-comments").child(feedkey);

        mCommentField = (EditText) findViewById(R.id.comment);
        //mCommentButton = (FloatingActionButton) findViewById(R.id.fabcomment);
        mCommentsRecycler = (RecyclerView) findViewById(R.id.recycler_comments);
        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));



        author = (TextView) findViewById(R.id.teacher_name_list);
        time = (TextView) findViewById(R.id.teacher_time);

        fb1 = (TextView) findViewById(R.id.fb1);
        fb2 = (TextView) findViewById(R.id.fb2);
        fb3 = (TextView) findViewById(R.id.fb3);
        fb4 = (TextView) findViewById(R.id.fb4);
        fb5 = (TextView) findViewById(R.id.fb5);
        fb6 = (TextView) findViewById(R.id.fb6);
        fb7 = (TextView) findViewById(R.id.fb7);
        fb8 = (TextView) findViewById(R.id.fb8);
        rm1 = (TextView) findViewById(R.id.rm1);
        rm2 = (TextView) findViewById(R.id.rm2);
        rm3 = (TextView) findViewById(R.id.rm3);
        rm4 = (TextView) findViewById(R.id.rm4);
        rm5 = (TextView) findViewById(R.id.rm5);
        rm6 = (TextView) findViewById(R.id.rm6);
        rm7 = (TextView) findViewById(R.id.rm7);
        rm8 = (TextView) findViewById(R.id.rm8);

        Intent intent = getIntent();


        String fb1s = intent.getStringExtra("f1");
        String fb2s = intent.getStringExtra("f2");
        String fb3s = intent.getStringExtra("f3");
        String fb4s = intent.getStringExtra("f4");
        String fb5s = intent.getStringExtra("f5");
        String fb6s = intent.getStringExtra("f6");
        String fb7s = intent.getStringExtra("f7");
        String fb8s = intent.getStringExtra("f8");
        String rm1s = intent.getStringExtra("rm1");
        String rm2s = intent.getStringExtra("rm2");
        String rm3s = intent.getStringExtra("rm3");
        String rm4s = intent.getStringExtra("rm4");
        String rm5s = intent.getStringExtra("rm5");
        String rm6s = intent.getStringExtra("rm6");
        String rm7s = intent.getStringExtra("rm7");
        String rm8s = intent.getStringExtra("rm8");
        String authors = intent.getStringExtra("author");
        String times = intent.getStringExtra("time");


        author.setText(authors);
        time.setText(times);
        fb1.setText(fb1s);
        fb2.setText(fb2s);
        fb3.setText(fb3s);
        fb4.setText(fb4s);
        fb5.setText(fb5s);
        fb6.setText(fb6s);
        fb7.setText(fb7s);
        fb8.setText(fb8s);
        rm1.setText("Remark: " + rm1s);
        rm2.setText("Remark: " + rm2s);
        rm3.setText("Remark: " + rm3s);
        rm4.setText("Remark: " + rm4s);
        rm5.setText("Remark: " + rm5s);
        rm6.setText("Remark: " + rm6s);
        rm7.setText("Remark: " + rm7s);
        rm8.setText("Remark: " + rm8s);
    }

    private void postComment() {
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("Managers-Profile").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                   managers_data user = dataSnapshot.getValue(managers_data.class);
                        String authorName = user.name;

                        // Create new comment object
                        String commentText = mCommentField.getText().toString();
                        Comment comment = new Comment(uid, authorName, commentText);

                        // Push the comment, it will appear in the list
                        mCommentsReference.push().setValue(comment);

                        // Clear the field
                        mCommentField.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Listen for comments
        mAdapter = new CommentAdapter(this, mCommentsReference);
        mCommentsRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Clean up comments listener
        mAdapter.cleanupListener();
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView authorView;
        public TextView bodyView;

        public CommentViewHolder(View itemView) {
            super(itemView);

            authorView = (TextView) itemView.findViewById(R.id.comment_author);
            bodyView = (TextView) itemView.findViewById(R.id.comment_body);
        }
    }

    private static class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mCommentIds = new ArrayList<>();
        private List<Comment> mComments = new ArrayList<>();

        public CommentAdapter(final Context context, DatabaseReference ref) {
            mContext = context;
            mDatabaseReference = ref;

            // Create child event listener
            // [START child_event_listener_recycler]
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    Comment comment = dataSnapshot.getValue(Comment.class);

                    // [START_EXCLUDE]
                    // Update RecyclerView
                    mCommentIds.add(dataSnapshot.getKey());
                    mComments.add(comment);
                    notifyItemInserted(mComments.size() - 1);
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                   // Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    Comment newComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Replace with the new data
                        mComments.set(commentIndex, newComment);

                        // Update the RecyclerView
                        notifyItemChanged(commentIndex);
                    } else {
                       // Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    //Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Remove data from the list
                        mCommentIds.remove(commentIndex);
                        mComments.remove(commentIndex);

                        // Update the RecyclerView
                        notifyItemRemoved(commentIndex);
                    } else {
                       // Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                   // Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    Comment movedComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                  //  Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(mContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            ref.addChildEventListener(childEventListener);
            // [END child_event_listener_recycler]

            // Store reference to listener so it can be removed on app stop
            mChildEventListener = childEventListener;
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            Comment comment = mComments.get(position);
            holder.authorView.setText(comment.author);
            holder.bodyView.setText(comment.text);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }
        }

    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}


