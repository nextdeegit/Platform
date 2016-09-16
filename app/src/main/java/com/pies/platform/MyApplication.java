package com.pies.platform;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nsikak  Thompson on 9/8/2016.
 */
public class MyApplication extends android.app.Application{

        @Override
        public void onCreate() {
            super.onCreate();
    /* Enable disk persistence  */
            if (!FirebaseApp.getApps(this).isEmpty()){
       FirebaseDatabase database =     FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
            }
        }}