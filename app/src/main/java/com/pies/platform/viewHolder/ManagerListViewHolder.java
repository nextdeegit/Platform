package com.pies.platform.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.model.managers.model.managers_data;
import com.pies.platform.teachersActivity.model.teacher_data;

public class ManagerListViewHolder extends RecyclerView.ViewHolder {

    public TextView tname;
    public TextView temail, region;
    public ImageView thumnial;

    public ManagerListViewHolder(View itemView) {
        super(itemView);

        tname = (TextView) itemView.findViewById(R.id.manager_name_list);
        temail= (TextView) itemView.findViewById(R.id.manager_email_list);
        thumnial = (ImageView) itemView.findViewById(R.id.manager_image_list);
        region = (TextView) itemView.findViewById(R.id.manager_region);

    }

    public void bindToPost1(managers_data post, View.OnClickListener starClickListenr) {
        tname.setText(post.getName());
        temail.setText(post.getEmail());
        region.setText(post.getRegion());
        thumnial.setImageResource(R.drawable.circle_2);


        //starView.setOnClickListener(starClickListener);
    }
}
