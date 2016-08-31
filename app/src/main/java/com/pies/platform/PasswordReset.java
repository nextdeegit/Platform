package com.pies.platform;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    EditText email;
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ProgressDialog p = new ProgressDialog(getApplicationContext());
        p.setMessage("Password Reseting....");

        email = (EditText) findViewById(R.id.emailreset);
        final String e = email.getText().toString();
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
            if(e.isEmpty()){
                Toast.makeText(PasswordReset.this, "Enter email address", Toast.LENGTH_SHORT).show();
            }else{
                p.show();
                auth.sendPasswordResetEmail(e)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {

                                p.hide();
                                    Toast.makeText(PasswordReset.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PasswordReset.this);
                                    builder.setMessage("A password reset link has been sent to your email address..")
                                            .setTitle("Done!")
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
                        });
            }

            }
        });
    }

}
