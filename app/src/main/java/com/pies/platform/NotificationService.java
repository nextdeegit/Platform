package com.pies.platform;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pies.platform.DefaultModel.NotificationItem;
import com.pies.platform.model.managers.Comment;

/**
 * Created by Nsikak  Thompson on 9/2/2016.
 */
public class NotificationService extends Service {
    private DatabaseReference mCommentsReference;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("Notifications");
        // Create child event listener
        // [START child_event_listener_recycler]
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
          NotificationItem comment = dataSnapshot.getValue(NotificationItem.class);
                String title = comment.title;
                String message = comment.message;
                int numMessages = 0;
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if(!comment.title.isEmpty()){
                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.ic_stat_name)
                                    .setContentTitle(title)
                                    .setContentText(message)

                    .setSound(alarmSound)
                            .setNumber(++numMessages)

                                    .setColor(getResources().getColor(R.color.colorPrimary));

                    // Creates an Intent for the Activity
                    Intent notifyIntent =
                            new Intent(getApplicationContext(), Home.class);
// Sets the Activity to start in a new, empty task
                    notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Creates the PendingIntent
                    PendingIntent notifyPendingIntent =
                            PendingIntent.getActivity(
                                    getApplicationContext(),
                                    0,
                                    notifyIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    // Puts the PendingIntent into the notification builder
                    mBuilder.setContentIntent(notifyPendingIntent);


                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(0, mBuilder.build());
                }

                // [END_EXCLUDE]
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
            /*    // A new comment has been added, add it to the displayed list
              Comment comment = dataSnapshot.getValue(Comment.class);
                String title = comment.author;
                String message = comment.text;
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if(!comment.author.isEmpty()){
                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.ic_stat_name)
                                    .setContentTitle(title)
                                    .setContentText(message)
                                    .setSound(alarmSound)
                                    .setColor(getResources().getColor(R.color.colorPrimary));
                    mBuilder.build();
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(0, mBuilder.build());
                }*/

                // [END_EXCLUDE]
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();


                // [END_EXCLUDE]
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //  Log.w(TAG, "postComments:onCancelled", databaseError.toException());
               // Toast.makeText(mContext, "Failed to load comments.",
                //        Toast.LENGTH_SHORT).show();
            }
        };
       mCommentsReference.addChildEventListener(childEventListener);
        // [END child_event_listener_recycler]

        // Store reference to listener so it can be removed on app stop
     //   mChildEventListener = childEventListener;

    }
}
