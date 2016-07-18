package com.pies.platform.teachersActivity.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.admin.model.Admin_Item;

import java.util.List;


public class Teacher_Adapter extends RecyclerView.Adapter<Teacher_Adapter.MyViewHolder> {

    private List<teacher_item> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtItem, numberitem, description;

        public MyViewHolder(View view) {
            super(view);
            imgItem = (ImageView) view.findViewById(R.id.thumdicon1);
            txtItem = (TextView) view.findViewById(R.id.titleName1);
            numberitem = (TextView) view.findViewById(R.id.number1);
            description = (TextView) view.findViewById(R.id.description1);
        }
    }


    public Teacher_Adapter(List<teacher_item> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_header_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        teacher_item item = moviesList.get(position);
        holder.imgItem.setImageDrawable(item.getThumbNail());
        holder.txtItem.setText(item.getTitleName());
        holder.numberitem.setText(item.getNumber());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}