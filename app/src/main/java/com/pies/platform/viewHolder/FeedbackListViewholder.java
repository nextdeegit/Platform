package com.pies.platform.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.teachersActivity.model.Feedback_item;

import java.util.ArrayList;

/**
 * Created by Nsikak  Thompson on 8/26/2016.
 */
public class FeedbackListViewholder extends RecyclerView.ViewHolder {
   public static ArrayList<String> Category_ID = new ArrayList<String>();
    public TextView tname;
    public TextView day,time,date;
    public ImageView thumnial;

    public FeedbackListViewholder(View itemView) {
        super(itemView);

      day = (TextView) itemView.findViewById(R.id.day);
       time = (TextView) itemView.findViewById(R.id.time);
        date = (TextView) itemView.findViewById(R.id.date);




    }

    public void bindToPost(Feedback_item post, View.OnClickListener starClickListener) {

if(post.getDay() != null){
    day.setText(post.getDay());
    time.setText(post.getTime());
    date.setText(post.getDate());
    Category_ID.add(post.getDay());

}


        //starView.setOnClickListener(starClickListener);
    }
}

