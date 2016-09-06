package com.pies.platform;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.pies.platform.admin.model.Add_Home_item;
import com.pies.platform.custom.SpinnerContext;
import com.pies.platform.teachersActivity.model.teacher_data;
import com.pies.platform.viewHolder.HomeListViewHolder;
import com.pies.platform.viewHolder.MyCustomAdapter;
import com.pies.platform.viewHolder.TeacherListViewHolder;

import java.util.ArrayList;
import java.util.List;
import android.view.WindowManager.LayoutParams;

public class Works_Detail extends AppCompatActivity {
private String teacherkey;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private TextView hom1,hom2,hom3;
    private String name,home_key1, home_key2,home_key3;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageButton add1,add2,add3;
    static SpinnerContext spinnerContext;
    private DatabaseReference mDatabase;
    ArrayList<String> homeData = new ArrayList<String>();
    private FirebaseUser mFirebaseuser;
    private      String uidU, hKey;
    String homeSelected,teacher_uid,region_select;
    private String userId;
    private ListView lt;
    ArrayAdapter<String> adapter;
    private FirebaseListAdapter<Add_Home_item> mAdapter;
    private ArrayList<Add_Home_item> data = new ArrayList<Add_Home_item>();
    DatabaseReference postRef;
    ArrayAdapter<String> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works__detail);
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

        addItemsOnContext();


     //   updateSpinner();
        mFirebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();


       collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //names.add("Select Home");

        teacherkey = getIntent().getStringExtra("post_key");
        teacher_uid = getIntent().getStringExtra("uid");
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Teachers-Added").child(teacherkey);

            getT(mPostReference);


                 lt = (ListView) findViewById(R.id.home_list1);

// Create a new Adapter
      adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        lt.setAdapter(adapter1);

        lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mPostReference = FirebaseDatabase.getInstance().getReference()
                        .child("Homes-Added");


                mPostReference.orderByChild("name")
                        .equalTo((String) lt.getItemAtPosition(i))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    //Toast.makeText(Works_Detail.this, firstChild.getKey().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                            public void onCancelled(FirebaseError firebaseError) { }
                        });

                //  hKey = postRef.getKey();
                // Add_Home_item item = mAdapter.getItem(i);
                // homeSelected = item.getName();
               // homeSelected = postRef.getKey().toString();
               // Toast.makeText(Works_Detail.this, homeSelected, Toast.LENGTH_SHORT).show();
            }
        });
        add1 = (ImageButton) findViewById(R.id.add_button1);
        add2 = (ImageButton) findViewById(R.id.add_button2);
        add3 = (ImageButton) findViewById(R.id.add_button3);



        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = mDatabase.child("Teachers-Profile").child(uidU).child(teacherkey);



              //  initiatePopupWindow();
                if(homeSelected.equals("") || homeSelected.equals("Select Home")){
                    Toast.makeText(Works_Detail.this, "Home not Selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    onButton1Clicked(databaseReference,homeSelected);
                }

   }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = mDatabase.child("Teachers-Profile").child(uidU).child(teacherkey);

                if(homeSelected.isEmpty() || homeSelected.equals("Select Home")){
                    Toast.makeText(Works_Detail.this, "Home not Selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    onButton2Clicked(databaseReference,homeSelected);
                }

            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = mDatabase.child("Teachers-Profile").child(uidU).child(teacherkey);

                if(homeSelected.isEmpty() || homeSelected.equals("Select Home")){
                    Toast.makeText(Works_Detail.this, "Home not Selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    onButton3Clicked(databaseReference,homeSelected);
                }

            }
        });
        hom1 = (TextView) findViewById(R.id.home1);
        hom2 = (TextView) findViewById(R.id.home2);
        hom3 = (TextView) findViewById(R.id.home3);




    }

    @Override
    public void onStart() {
        super.onStart();
updateList();
        // Add value event listener to the post
// Initialize Database



    }

    public void getHomeNm(){
       // Toast.makeText(Works_Detail.this, teacherkey +"" + teacher_uid, Toast.LENGTH_SHORT).show();

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Teachers-Profile").child(userId).child(teacherkey);
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                teacher_data teacher = dataSnapshot.getValue(teacher_data.class);
                if(teacher.uid != null){
                    uidU = teacher.uid.toString();

                }
                if(teacher.home1 != null){
                  home_key1 = teacher.home1;
                    getHomeNm1();
                }
                if(teacher.home2 != null){
                    home_key2 = teacher.home2;
                    getHomeNm2();
                }
                if(teacher.home3 != null){
                    home_key3 = teacher.home3;
                    getHomeNm3();
                }

                if(teacher.getName() != null){
                    name = teacher.getName().toString();
                    collapsingToolbarLayout.setTitle(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(Works_Detail.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }
    public void addItemsOnContext() {
        spinnerContext = (SpinnerContext) findViewById(R.id.spinnerContext);

        homeData.add("Select Home");
        homeData.add("Cross River");
        // Array adapter





                    spinnerContext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                          homeSelected = spinnerContext.getSelectedItem().toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void getHomeNm1(){
        // Toast.makeText(Works_Detail.this, teacherkey +"" + teacher_uid, Toast.LENGTH_SHORT).show();

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Teachers-Profile").child(userId).child(teacherkey);
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                teacher_data teacher = dataSnapshot.getValue(teacher_data.class);

                if(teacher.home1 != null){
                    hom1.setText("Home 1 : " +  teacher.home1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                Toast.makeText(Works_Detail.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }
    public void getHomeNm2(){
        // Toast.makeText(Works_Detail.this, teacherkey +"" + teacher_uid, Toast.LENGTH_SHORT).show();

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Teachers-Profile").child(userId).child(teacherkey);
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                teacher_data teacher = dataSnapshot.getValue(teacher_data.class);

                if(teacher.home2 != null){
                    hom2.setText("Home 2 : " +  teacher.home2);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                Toast.makeText(Works_Detail.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }
    public void getHomeNm3(){
        // Toast.makeText(Works_Detail.this, teacherkey +"" + teacher_uid, Toast.LENGTH_SHORT).show();

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Teachers-Profile").child(userId).child(teacherkey);
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                teacher_data teacher = dataSnapshot.getValue(teacher_data.class);

                if(teacher.home3 != null){
                    hom3.setText("Home 3 : " +  teacher.home3);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                Toast.makeText(Works_Detail.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }

    private  void updateList(){

        Query queryRef = getQuery(mDatabase);
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("Homes-Added");

        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
               /* if(dataSnapshot != null){
                    String h = dataSnapshot.child("name").getValue().toString();
                    //Add_Home_item item = dataSnapshot.child("name").getValue(Add_Home_item.class);
                    homeData.add(h);
                }*/

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                   String h = ds.child("name").getValue().toString();
                    Toast.makeText(Works_Detail.this, h, Toast.LENGTH_SHORT).show();
                    Add_Home_item item = dataSnapshot.getValue(Add_Home_item.class);
                    homeData.add(h);
                }


             // homeData.add(item.name);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                Toast.makeText(Works_Detail.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
        adapter = new ArrayAdapter<>(Works_Detail.this, android.R.layout.simple_list_item_1,homeData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spinnerContext.setAdapter(adapter);

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;

       /* mPostReference.addChildEventListener(new ChildEventListener() {
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

*/





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

    // [START post_stars_transaction]
    private void onButton1Clicked(DatabaseReference postRef, final String selected_home) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
               teacher_data p = mutableData.getValue(teacher_data.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }
                if(p.home1.contentEquals("")){
                    p.home1 = p.home1 + selected_home;
                }
                else{
                    p.home1 = selected_home;
                }


                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
               // Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                Toast.makeText(Works_Detail.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // [END post_stars_transaction]


    @Override
    protected void onStop() {
        super.onStop();
        adapter.clear();
    }

    // [START post_stars_transaction]
    private void onButton2Clicked(DatabaseReference postRef, final String selected_home) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                teacher_data p = mutableData.getValue(teacher_data.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }
                if(p.home2.contentEquals("")){
                    p.home2 = p.home2 + selected_home;
                }
                else{
                    p.home2 = selected_home;
                }


                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                // Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                Toast.makeText(Works_Detail.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // [START post_stars_transaction]
    private void onButton3Clicked(DatabaseReference postRef, final String selected_home) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                teacher_data p = mutableData.getValue(teacher_data.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }
                if(p.home3.contentEquals("")){
                    p.home3 = p.home3 + selected_home;
                }
                else{
                    p.home3 = selected_home;
                }


                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                // Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                Toast.makeText(Works_Detail.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getT(DatabaseReference databaseReference){

        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                teacher_data teacher = dataSnapshot.getValue(teacher_data.class);
                if(teacher.uid != null){
                    userId = teacher.uid;
                    getHomeNm();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(getApplicationContext(), "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        databaseReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }

    private class Updater extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            updateList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lt.setAdapter(mAdapter);




        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        homeSelected = "";
    }

    public void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Works_Detail.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.homelistview,
                    (ViewGroup) findViewById(R.id.layer_list));


          PopupWindow  pwindo = new PopupWindow(layout, 300, 370, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
