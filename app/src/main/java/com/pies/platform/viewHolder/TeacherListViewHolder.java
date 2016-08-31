package com.pies.platform.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.teachersActivity.model.teacher_data;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherListViewHolder extends RecyclerView.ViewHolder {

    public TextView tname;
    public TextView temail, region, index;
    public ImageView thumnial;

    public TeacherListViewHolder(View itemView) {
        super(itemView);

        tname = (TextView) itemView.findViewById(R.id.teacher_name_list);
        temail= (TextView) itemView.findViewById(R.id.teacher_email_list);
        thumnial = (ImageView) itemView.findViewById(R.id.teacher_image_list);
        region = (TextView) itemView.findViewById(R.id.teacher_region);
        index = (TextView) itemView.findViewById(R.id.textView3);

    }

    public void bindToPost(teacher_data post, View.OnClickListener starClickListener) {
        if(post.getName() != null){
            String n = post.getName();
            String first = n.substring(0,1);
            index.setText(first);
        }

        tname.setText(post.getName());
        temail.setText(post.getEmail());
        region.setText(post.getRegion());


        //starView.setOnClickListener(starClickListener);
    }
}
