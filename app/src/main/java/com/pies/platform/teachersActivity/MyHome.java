package com.pies.platform.teachersActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pies.platform.HomeAdapter;
import com.pies.platform.Login;
import com.pies.platform.R;
import com.pies.platform.Verification;
import com.pies.platform.Works_Detail;
import com.pies.platform.admin.Admin_dashboard;
import com.pies.platform.admin.model.Add_Home_item;

import com.pies.platform.viewHolder.Teacher_Assigned_Home;

import java.util.ArrayList;
import java.util.List;

public class MyHome extends AppCompatActivity {
    //private FirebaseRecyclerAdapter<Add_Home_item, Teacher_Assigned_Home> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]
    String postKey;
    FirebaseAuth mFirebaseAuth;

    private FirebaseUser mFirebaseUser;
    private String teacherNam, teacherEmail;
    ProgressBar pd;
   public LocationManager locationManager;
    Criteria criteria;
    Location location;
     public static double lat;
    public static double longt;
    AlertDialog alert;
    int position;
    ArrayList<String> homeData = new ArrayList<String>();
    private DatabaseReference mPostReference;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ArrayAdapter<String> adapter;
    private ArrayList<Add_Home_item> movieList = new ArrayList<>();
    private Teacher_Assigned_Home mAdapter;
    String home1 ="";

    String home2 ="";
    String home3 = "";
    String hom1lat, hom1long;
    Teacher_Assigned_Home.MyViewHolder hv;
    Add_Home_item a;
   public String provider,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = getIntent().getStringExtra("name");



        home1 = getIntent().getStringExtra("home1");
        home2 = getIntent().getStringExtra("home2");
        home3 = getIntent().getStringExtra("home3");
        if(home1 != "" && home2 != "" && home3 != ""){
            updateList();
            updateList2();
            updateList3();
            Toast.makeText(MyHome.this, home1, Toast.LENGTH_SHORT).show();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        alert = builder.create();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser == null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {

                }
            }
        };
        pd = (ProgressBar) findViewById(R.id.donut);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout1);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.green),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.green));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mAdapter != null){
               mRecycler.setAdapter(mAdapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                else if(!home1.isEmpty()) {
                    updateList();
                    updateList2();
                    updateList3();
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                //up();
            }});

        mRecycler = (RecyclerView) findViewById(R.id.recycler_teach);
        mRecycler.setHasFixedSize(true);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getApplicationContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        mAdapter = new Teacher_Assigned_Home(movieList);
        mRecycler.setAdapter(mAdapter);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        Add_Home_item item = movieList.get(position);

        mRecycler.addOnItemTouchListener(new Admin_dashboard.RecyclerTouchListener(getApplicationContext(), mRecycler, new Admin_dashboard.ClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view, final int position) {



                PopupMenu pop_up = new PopupMenu(getApplicationContext(), view,R.style.AppTheme);

                pop_up.getMenuInflater().inflate(R.menu.menu_popup, pop_up.getMenu());

                pop_up.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Add_Home_item item_home = movieList.get(position);
                     switch (item.getItemId()){

                            case R.id.obj:
                            //do something
                            Intent intent = new Intent(getApplicationContext(),Create_Obj.class);
                            intent.putExtra("home_name", item_home.getName());
                            intent.putExtra("teacher_name",name);
                            startActivity(intent);
                            return true;
                            case R.id.fback:
                            //do something
                            Intent intent1 = new Intent(getApplicationContext(), Feedback.class);
                            intent1.putExtra("home_name", item_home.getName());
                            intent1.putExtra("teacher_name",name);
                            startActivity(intent1);
                                return  true;
                            default:
                                return true;
                        }



                    }
                });
                pop_up.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
//prepareMovieData();

        //   locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alert.show();
        } else {
            alert.hide();
            // Getting the name of the provider that meets the criteria
            provider = locationManager.getBestProvider(criteria, false);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {

                lat = location.getLatitude();

                longt = location.getLongitude();
                // Toast.makeText(MapsActivity.this, "long:" + d + "lat:" + c, Toast.LENGTH_SHORT).show();


            }
        }
    }


    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        // Query recentPostsQuery = databaseReference.child("Teachers-Added").child("SPUE1xO0JbTaXUqM80xPmszCUIK2");
        // [END recent_posts_query]
        ;
        return databaseReference.child("Homes-Added-toAssign").child(home1);
    }


    @Override
    protected void onStart() {
        super.onStart();





    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alert.show();
        } else {
            alert.hide();
            // Getting the name of the provider that meets the criteria
            String provider = locationManager.getBestProvider(criteria, false);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {

                lat = location.getLatitude();

                longt = location.getLongitude();
                // Toast.makeText(MapsActivity.this, "long:" + d + "lat:" + c, Toast.LENGTH_SHORT).show();


            }
        }
    }

    private  void updateList(){

//        Query queryRef = getQuery(mDatabase);
        mPostReference = FirebaseDatabase.getInstance().getReference().
        child("Homes-Added-toAssign").child(home1);

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


                    String h = dataSnapshot.child("name").getValue().toString();
                //    Toast.makeText(Works_Detail.this, h, Toast.LENGTH_SHORT).show();
                    String add = dataSnapshot.child("address").getValue().toString();
                hom1lat = dataSnapshot.child("latitude").getValue().toString();
                hom1long = dataSnapshot.child("longitude").getValue().toString();
                double hlat = Double.parseDouble(hom1lat);
                double hlong = Double.parseDouble(hom1long);


                    Add_Home_item item = dataSnapshot.getValue(Add_Home_item.class)
                    //homeData.add(h);
                ;
                    Add_Home_item manager = new Add_Home_item(5.015065000000001,7.912905,h,add,"csfsfsf","ssvsv");
                    movieList.add(manager);


                    mAdapter.notifyDataSetChanged();

                //if(a.latitude != 0 && a.longitude != 0|| lat != 0 && longt != 0) {



                }


                // homeData.add(item.name);



            //}

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
               // Toast.makeText(Works_Detail.this, "Failed to load post.",
                      //  Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
//        pd.setVisibility(View.INVISIBLE);
        mPostReference.addValueEventListener(postListener);






    }

    private  void updateList2(){

//        Query queryRef = getQuery(mDatabase);
        mPostReference = FirebaseDatabase.getInstance().getReference().
                child("Homes-Added-toAssign").child(home2);

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


                String h = dataSnapshot.child("name").getValue().toString();
                //    Toast.makeText(Works_Detail.this, h, Toast.LENGTH_SHORT).show();
                String add = dataSnapshot.child("address").getValue().toString();
                hom1lat = dataSnapshot.child("latitude").getValue().toString();
                hom1long = dataSnapshot.child("longitude").getValue().toString();
                double hlat = Double.parseDouble(hom1lat);
                double hlong = Double.parseDouble(hom1long);


                Add_Home_item item = dataSnapshot.getValue(Add_Home_item.class);
                //homeData.add(h);

                Add_Home_item manager = new Add_Home_item(5.015065000000001,7.912905,h,add,"csfsfsf","ssvsv");
                movieList.add(manager);


                mAdapter.notifyDataSetChanged();
                //if(a.latitude != 0 && a.longitude != 0|| lat != 0 && longt != 0) {



            }


            // homeData.add(item.name);



            //}

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                // Toast.makeText(Works_Detail.this, "Failed to load post.",
                //  Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);






    }


    private  void updateList3(){

//        Query queryRef = getQuery(mDatabase);
        mPostReference = FirebaseDatabase.getInstance().getReference().
                child("Homes-Added-toAssign").child(home1);

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


                String h = dataSnapshot.child("name").getValue().toString();
                //    Toast.makeText(Works_Detail.this, h, Toast.LENGTH_SHORT).show();
                String add = dataSnapshot.child("address").getValue().toString();
                hom1lat = dataSnapshot.child("latitude").getValue().toString();
                hom1long = dataSnapshot.child("longitude").getValue().toString();
                double hlat = Double.parseDouble(hom1lat);
                double hlong = Double.parseDouble(hom1long);


                Add_Home_item item = dataSnapshot.getValue(Add_Home_item.class);
                //homeData.add(h);

                Add_Home_item manager = new Add_Home_item(5.015065000000001,7.912905,h,add,"csfsfsf","ssvsv");
                movieList.add(manager);


                mAdapter.notifyDataSetChanged();
                //if(a.latitude != 0 && a.longitude != 0|| lat != 0 && longt != 0) {



            }


            // homeData.add(item.name);



            //}

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                // Toast.makeText(Works_Detail.this, "Failed to load post.",
                //  Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);






    }
    private void prepareMovieData() {
   Add_Home_item manager = new Add_Home_item(0.1,0.1,"cvvvc","cvdvsv","csfsfsf","ssvsv");
        movieList.add(manager);


        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
