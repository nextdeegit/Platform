package com.pies.platform.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.model.managers.model.managers_data;
import com.pies.platform.teachersActivity.model.Home_data;

public class HomeListViewHolder extends RecyclerView.ViewHolder {

    public TextView tname;
    public TextView temail, region;
    public ImageView thumnial;

    public HomeListViewHolder(View itemView) {
        super(itemView);

        tname = (TextView) itemView.findViewById(R.id.home_names);


    }

    public void bindToPost1(Home_data post, View.OnClickListener starClickListenr) {
        tname.setText(post.getName());



        //starView.setOnClickListener(starClickListener);
    }
}