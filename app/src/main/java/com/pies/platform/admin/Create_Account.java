package com.pies.platform.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pies.platform.Login;
import com.pies.platform.R;
import com.pies.platform.admin.model.Admin_data;
import com.pies.platform.teachersActivity.model.teacher_data;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Create_Account extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private Spinner spinner ,spinner1;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.input_name) EditText teachersName;
    // @Bind(R.id.link_login) TextView _loginLink;
   // @Bind(R.id.input_password_reentered) EditText renterPassword;
     FirebaseAuth mAuth;
     FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    // [START declare_database_ref]
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseUser mFirebaseUser;
    // [END declare_database_ref]
    String uidMe;
    ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;
    String userType, selectedRegion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);


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
                    Toast.makeText(Create_Account.this,test, Toast.LENGTH_SHORT).show();
                   // createAdmin("","Paul","edakndk@gmail.com", "cagjagkcjgak","Admin");
                }else{

                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }};
        progressDialog = new ProgressDialog(Create_Account.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setCancelable(false);
        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.users, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.regions, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);





        validate();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = _passwordText.getText().toString();
                final String email = _emailText.getText().toString();
                final String username = teachersName.getText().toString();
               // final String reenterPassword = renterPassword.getText().toString();
                //password = password.trim();
                //email = email.trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Create_Account.this);
                    builder.setMessage("Incomplete Credentials")
                            .setTitle("Please complete the form")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();}


               else if(userType.equals("Choose UserType") && selectedRegion.equals("Choose Region")){
                    Toast.makeText(Create_Account.this, "Incomplete Form", Toast.LENGTH_SHORT).show();



                }
                else if((userType.equals("Manager") || userType.equals("Student") || userType.equals("Teacher")) && selectedRegion.equals("Choose Region")) {
                    Toast.makeText(Create_Account.this, "Region not selected", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.show();
                    // [START create_user_with_email]
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Create_Account.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Create_Account.this, "Authentication failed." + email + password,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                       // createAdmin("erdffdfdfd","paul akpan","edak@gmail.com", "tommytom","admin");
                                        FirebaseUser user = mAuth.getCurrentUser();





                                        uidMe = user.getUid();

                                        if(userType.equals("Teacher")){
                                            createTeacher(uidMe,username,email,password, userType,selectedRegion);
                                        }
                                        else if(userType.equals("Manager")){
                                            createManager(uidMe,username,email,password, userType, selectedRegion);
                                        }

                                        else  if(userType.equals("Admin")){
                                            createAdmin(uidMe,username,email, userType);
                                        }


                                    }



                                }
                            });
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
        //    createAdmin("erdffdfdfd","paul akpan","edak@gmail.com", "tommytom","admin");
            mDatabase.child("all").setValue("sikak");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // parent.getItemAtPosition(pos)
        userType = spinner.getSelectedItem().toString();
        if(userType.equals("Admin")){
            spinner1.setVisibility(View.GONE);
        }
        else{
            spinner1.setVisibility(View.VISIBLE);
        }
        selectedRegion = spinner1.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean validate() {
        boolean valid = true;

        String name = teachersName.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 5) {
            teachersName.setError("at least 5 characters");
            valid = false;
        } else {
            teachersName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 8) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    // [START write_fan_out]
    private void createTeacher( String uid,String username, String email,String password, String usertype, String region) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("all-user").push().getKey();
        teacher_data teacher = new teacher_data( uid,username,email, password,usertype, region);
        Map<String, Object> postValues = teacher.toMap1();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Teachers-Added/" + key , postValues);
        childUpdates.put("/Teachers-Profile/" + uid + "/" + key, postValues);
        childUpdates.put("/auth-user/" + uid ,postValues);
        childUpdates.put("/all-user/" + key , postValues);
        mDatabase.updateChildren(childUpdates).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Create_Account.this, "Teacher not Added" ,
                            Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Create_Account.this);
                    builder.setMessage("Teacher Added")
                            .setTitle("Welcome to PIES")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //FirebaseAuth.getInstance().signOut();
                                    mAuth.signOut();
                                    startActivity(new Intent(getApplicationContext(), Login.class));

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

                progressDialog.hide();
            }
        });


    }
    // [START write_fan_out]
    private void createManager( String uid,String username, String email,String password, String usertype, String region) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("all-user").push().getKey();
        teacher_data teacher = new teacher_data( uid,username,password, email,usertype, region);
        Map<String, Object> postValues = teacher.toMap1();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Managers-Added/" + key , postValues);
        childUpdates.put("/Managers-Profile/" + uid + "/" + key, postValues);
        childUpdates.put("/all-user/" + key , postValues);
        mDatabase.updateChildren(childUpdates).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Create_Account.this, "Manager not Added" ,
                            Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Create_Account.this);
                    builder.setMessage("Manager Added")
                            .setTitle("Welcome to PIES")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //FirebaseAuth.getInstance().signOut();
                                    mAuth.signOut();
                                    startActivity(new Intent(getApplicationContext(), Login.class));

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

                progressDialog.hide();
            }
        });

    }
    // [START write_fan_out]
    private void createAdmin(String u,String user, String email_one, String usertype1) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("all-user").push().getKey();
        Admin_data admin = new Admin_data( u,user, email_one,usertype1);
        Map<String, Object> postValues = admin.toAdmin();

        Map<String, Object> childUpdates = new HashMap<>();
       childUpdates.put("/admin-Added/" + key , postValues);
        childUpdates.put("/admin-Profile/" + u  , postValues);
        childUpdates.put("/all-user/" + key + "/" + u , postValues);
        childUpdates.put("/auth-user/" + u ,postValues);
        mDatabase.updateChildren(childUpdates).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Create_Account.this, "Admin not Added" ,
                            Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Create_Account.this);
                    builder.setMessage("Admin Added")
                            .setTitle("Welcome to PIES")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //FirebaseAuth.getInstance().signOut();
                                    mAuth.signOut();
                                    startActivity(new Intent(getApplicationContext(), Login.class));

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                progressDialog.hide();

            }
        });;

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
