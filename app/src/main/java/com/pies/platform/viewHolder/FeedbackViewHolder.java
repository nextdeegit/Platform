package com.pies.platform.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.teachersActivity.model.Feedback_item;
import com.pies.platform.teachersActivity.model.teacher_data;

/**
 * Created by Nsikak  Thompson on 8/25/2016.
 */
public class FeedbackViewHolder extends RecyclerView.ViewHolder {

    public TextView tname;
    public TextView fb1,fb2,fb3,fb4,fb5,fb6,fb7,fb8,rm1,rm2,rm3,rm4,rm5,rm6,rm7,rm8;
    public ImageView thumnial;

    public FeedbackViewHolder(View itemView) {
        super(itemView);

        fb1 = (TextView) itemView.findViewById(R.id.fb1);
        fb2 = (TextView) itemView.findViewById(R.id.fb2);
        fb3 = (TextView) itemView.findViewById(R.id.fb3);
        fb4 = (TextView) itemView.findViewById(R.id.fb4);
        fb5 = (TextView) itemView.findViewById(R.id.fb5);
        fb6 = (TextView) itemView.findViewById(R.id.fb6);
        fb7 = (TextView) itemView.findViewById(R.id.fb7);
        fb8 = (TextView) itemView.findViewById(R.id.fb8);
        rm1 = (TextView) itemView.findViewById(R.id.rm1);
        rm2 = (TextView) itemView.findViewById(R.id.rm2);
        rm3 = (TextView) itemView.findViewById(R.id.rm3);
        rm4 = (TextView) itemView.findViewById(R.id.rm4);
        rm5 = (TextView) itemView.findViewById(R.id.rm5);
        rm6 = (TextView) itemView.findViewById(R.id.rm6);
        rm7 = (TextView) itemView.findViewById(R.id.rm7);
        rm8 = (TextView) itemView.findViewById(R.id.rm8);



    }

    public void bindToPost(Feedback_item post, View.OnClickListener starClickListener) {

        fb1.setText(post.getFb1());
        fb2.setText(post.getFb2());
        fb3.setText(post.getFb3());
        fb4.setText(post.getFb4());
        fb5.setText(post.getFb5());
        fb6.setText(post.getFb6());
        fb7.setText(post.getFb7());
        fb8.setText(post.getFb8());
        rm1.setText("Remark: " + post.getRm1());
        rm2.setText("Remark: " +post.getRm2());
        rm3.setText("Remark: " +post.getRm3());
        rm4.setText("Remark: " +post.getRm4());
        rm5.setText("Remark: " +post.getRm5());
        rm6.setText("Remark: " +post.getRm6());
        rm7.setText("Remark: " +post.getRm7());
        rm8.setText("Remark: " +post.getRm8());


        //starView.setOnClickListener(starClickListener);
    }
}
