package com.pies.platform.teachersActivity;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.database.Query;
        import com.pies.platform.R;

        import android.app.Activity;
        import android.app.Fragment;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;

        import android.os.Bundle;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.GridView;
        import android.widget.ListView;
        import android.widget.ProgressBar;
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
        import com.pies.platform.Works_Detail;
        import com.pies.platform.admin.Admin_dashboard;
        import com.pies.platform.admin.Create_Account;
        import com.pies.platform.admin.model.AdminAdapter;
        import com.pies.platform.admin.model.Admin_Item;
        import com.pies.platform.model_users.Users;
        import com.pies.platform.teachersActivity.model.Teacher_Adapter;
        import com.pies.platform.teachersActivity.model.teacher_data;
        import com.pies.platform.teachersActivity.model.teacher_item;
        import com.pies.platform.viewHolder.TeacherListViewHolder;

        import java.util.ArrayList;
        import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssignmentFragment extends android.support.v4.app.Fragment {
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
    String postKey;
    private FirebaseRecyclerAdapter<teacher_data, TeacherListViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    ProgressDialog progressDialog;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private ProgressBar pd;
    public AssignmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment, container, false);


        mFirebaseAuth = FirebaseAuth.getInstance();
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]
pd = (ProgressBar) root.findViewById(R.id.progress1);
        mRecycler = (RecyclerView) root.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);
    updateList();
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





    }



    public void bindGridview() {

        new Updater(getActivity(),mRecycler,pd,this).execute();
    }

    public void updateList(){
        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<teacher_data, TeacherListViewHolder>(teacher_data.class, R.layout.teachers_list,
                TeacherListViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final TeacherListViewHolder viewHolder, final teacher_data model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                postKey = postRef.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity


                        Intent intent = new Intent(getActivity(), Works_Detail.class);
                        intent.putExtra("post_key", postRef.getKey().toString());
                        intent.putExtra("uid", userId);

                        startActivity(intent);
                    }
                });
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });


            }
        };
        pd.setVisibility(View.INVISIBLE);
        mRecycler.setAdapter(mAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
            postKey = "";
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
        return databaseReference.child("Teachers-Added");
    }



    @Override
    public void onPause() {
        super.onPause();
      // mPostListener = null;
    }
    public void showLoader()
    {
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                pd.setVisibility(View.VISIBLE);
            }
        });
    }
   public void hideLoader()
    {
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                pd.setVisibility(View.INVISIBLE);
            }
        });
    }


  private class Updater extends AsyncTask<Void,Void,Void> {
       RecyclerView list;
       Activity context;
       ProgressBar pt;
       AssignmentFragment container;


        public Updater(Activity context,RecyclerView list, ProgressBar pt, AssignmentFragment container){
            this.list = list;
            this.context = context;
            this.pt = pt;
            this.container = container;
        }
        @Override
        protected void onPreExecute() {
          // container.showLoader();
            container.pd.setVisibility(View.VISIBLE);
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... voids) {
           container.updateList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            list.setAdapter(mAdapter);
            //container.hideLoader();
            container.pd.setVisibility(View.INVISIBLE);
            super.onPostExecute(aVoid);







        }
    }
}