package com.pies.platform.teachersActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pies.platform.R;
import com.pies.platform.teachersActivity.model.Feedback_item;
import com.pies.platform.teachersActivity.model.ObjItem;

import java.util.HashMap;
import java.util.Map;

public class Create_Obj extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__obj);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.signout) {


            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
    // [START write_fan_out]
    private void createObj(String subject, String topic,String objectives, String author,String sent_time, String date, String day) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("all-Objectives").push().getKey();
       ObjItem obj = new ObjItem(subject,topic,date,objectives,sent_time,day,author);
        Map<String, Object> postValues = obj.toObj();

        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/all-Objectives/" + homekey  + "/" + key, postValues);
       /* childUpdates.put("/Teachers-Profile/" + uid + "/" + key, postValues);
        childUpdates.put("/auth-user/" + uid, postValues);
        childUpdates.put("/all-user/" + key, postValues);*/
        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Create_Obj.this, " not sent",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Create_Obj.this, "Successfully Sent", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }
}
