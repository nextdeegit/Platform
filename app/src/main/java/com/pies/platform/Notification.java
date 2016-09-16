package com.pies.platform;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pies.platform.DefaultModel.NotificationItem;
import com.pies.platform.admin.MapsActivity;
import com.pies.platform.admin.model.Add_Home_item;

import java.util.HashMap;
import java.util.Map;

public class Notification extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    EditText mtitle, eMessage;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(Notification.this);
        progressDialog.setMessage("Sending....");
        progressDialog.setCancelable(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mtitle = (EditText) findViewById(R.id.input_title);
        eMessage = (EditText) findViewById(R.id.input_message);
        send = (Button) findViewById(R.id.btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mtitle.getText().toString();
                String message = eMessage.getText().toString();
                if(title.isEmpty() || message.isEmpty()){
                    Toast.makeText(Notification.this, "Form empty", Toast.LENGTH_SHORT).show();
                }else {
                    createNotification(title,message);
                }

            }
        });
    }
    // [START write_fan_out]
    private void createNotification(String title,String message) {
        progressDialog.show();
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("Notifications").push().getKey();
        NotificationItem notify = new NotificationItem(title,message);
        Map<String, Object> postValues = notify.toSendNoti();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/Notifications/" + key, postValues);


        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Notification.this, "Message not sent",
                            Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
                    builder.setMessage("Message Sent")
                            .setTitle("Successfully")
                            .setCancelable(true)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //FirebaseAuth.getInstance().signOut();



                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
                progressDialog.hide();

            }
        });
    }


}
