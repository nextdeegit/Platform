package com.pies.platform;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pies.platform.admin.Admin_dashboard;
import com.pies.platform.admin.Create_Account;
import com.pies.platform.admin.model.AdminAdapter;
import com.pies.platform.admin.model.Admin_Item;
import com.pies.platform.custom.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private List<Admin_Item> movieList = new ArrayList<>();
    private RecyclerView recyclerView2;
    private HomeAdapter mAdapter;
   private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    // [START declare_database_ref]
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseUser mFirebaseUser;
    // [END declare_database_ref]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [START auth_state_listener]


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());
                    String test =  mFirebaseUser.getUid();

                  //  Toast.makeText(Home.this,test, Toast.LENGTH_SHORT).show();
                    // createAdmin("","Paul","edakndk@gmail.com", "cagjagkcjgak","Admin");
                }else{

                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }};

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_home);
        mAdapter = new HomeAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Home.this);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setHasFixedSize(true);
        recyclerView2.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));


        recyclerView2.addOnItemTouchListener(new Admin_dashboard.RecyclerTouchListener(getApplicationContext(), recyclerView2, new Admin_dashboard.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(position == 0){
                    startActivity(new Intent(getApplicationContext(), Verification.class));
                }
                else if(position == 1){

                }
                else if (position== 2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setMessage("You can contact us via this email address  aniekanpaul@gmail.com"  )
                            .setTitle("Contact")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //FirebaseAuth.getInstance().signOut();
                                  dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
new Ui().execute();

    }

    private void prepareMovieData() {
        Admin_Item manager = new Admin_Item("Dashboard", "","Click to Perform More Task ", getResources().getDrawable(R.drawable.y));
        movieList.add(manager);

        manager = new Admin_Item("News Feeds","Coming Soon", " Get Lastest News Update", getResources().getDrawable(R.drawable.fd));
        movieList.add(manager);

        manager = new Admin_Item("Contact Us","", "Send us your Suggestions and Questions ", getResources().getDrawable(R.drawable.c));
        movieList.add(manager);

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {

            FirebaseAuth.getInstance().signOut();
            finish();
        return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if(!isNetworkAvailable()){
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage("Failure to connect to the internet, please check your network connection")
                    .setTitle("No Network Connection")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //FirebaseAuth.getInstance().signOut();
                         finish();

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class Ui extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


            prepareMovieData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView2.setAdapter(mAdapter);
        }
    }
}
