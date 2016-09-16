package com.pies.platform.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.teachersActivity.model.Feedback_item;
import com.pies.platform.teachersActivity.model.ObjItem;

import java.util.ArrayList;

/**
 * Created by Nsikak  Thompson on 9/9/2016.
 */
public class ObjectivesViewHolder extends RecyclerView.ViewHolder {
    public static ArrayList<String> Category_ID = new ArrayList<String>();
    public TextView tname;
    public TextView day,time,date;
    public ImageView thumnial;

    public ObjectivesViewHolder(View itemView) {
        super(itemView);

        day = (TextView) itemView.findViewById(R.id.day);
        time = (TextView) itemView.findViewById(R.id.timed);
        date = (TextView) itemView.findViewById(R.id.date);




    }

    public void bindToPost(ObjItem post, View.OnClickListener starClickListener) {

        if(post.getDate() != null){
            day.setText(post.getDay());
            time.setText(post.getSent_time());
            date.setText(post.getDate());


        }


        //starView.setOnClickListener(starClickListener);
    }
}
