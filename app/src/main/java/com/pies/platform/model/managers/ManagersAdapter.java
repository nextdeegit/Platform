package com.pies.platform.model.managers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;


import com.pies.platform.R;

public class ManagersAdapter extends RecyclerView.Adapter<ManagersAdapter.MyViewHolder> {

    private List<ManagersItem> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtItem, numberitem, description;

        public MyViewHolder(View view) {
            super(view);
            imgItem = (ImageView) view.findViewById(R.id.thumdicon4);
            txtItem = (TextView) view.findViewById(R.id.titleName4);
           // numberitem = (TextView) view.findViewById(R.id.number4);
            description = (TextView) view.findViewById(R.id.description4);
        }
    }


    public ManagersAdapter(List<ManagersItem> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manager_header_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ManagersItem item = moviesList.get(position);
        holder.imgItem.setImageDrawable(item.getThumbNail());
        holder.txtItem.setText(item.getTitleName());
//        holder.numberitem.setText(item.getNumber());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}