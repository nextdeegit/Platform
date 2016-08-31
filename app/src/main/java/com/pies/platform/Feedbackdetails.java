package com.pies.platform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Feedbackdetails extends AppCompatActivity {
    public TextView fb1,fb2,fb3,fb4,fb5,fb6,fb7,fb8,rm1,rm2,rm3,rm4,rm5,rm6,rm7,rm8,author,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackdetails);
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

}
