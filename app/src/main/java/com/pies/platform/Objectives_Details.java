package com.pies.platform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Objectives_Details extends AppCompatActivity {
TextView author,time,subject,topic,objectives;
    String authorname,timeD,key,subj,tpic,obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectives__details);
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
        subject = (TextView) findViewById(R.id.subject);
        topic = (TextView) findViewById(R.id.topic);
        objectives = (TextView) findViewById(R.id.setObj);

        Intent intent = getIntent();
        if(intent != null){
            authorname = intent.getStringExtra("author");
           timeD = intent.getStringExtra("time");
           key = intent.getStringExtra("obj_key");
           subj = intent.getStringExtra("subject");
            tpic = intent.getStringExtra("topic");
            obj = intent.getStringExtra("objec");

            author.setText(authorname);
            time.setText(timeD);
            subject.setText(subj);
            topic.setText(tpic);
            objectives.setText(obj);
        }



    }

}
