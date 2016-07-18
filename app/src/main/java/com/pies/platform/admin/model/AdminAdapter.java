package com.pies.platform.admin.model;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;


import com.pies.platform.R;
import com.pies.platform.model.managers.ManagersItem;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {

    private List<Admin_Item> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtItem, numberitem, description;

        public MyViewHolder(View view) {
            super(view);
            imgItem = (ImageView) view.findViewById(R.id.thumdicon);
            txtItem = (TextView) view.findViewById(R.id.titleName);
            numberitem = (TextView) view.findViewById(R.id.number);
            description = (TextView) view.findViewById(R.id.description);
        }
    }


    public AdminAdapter(List<Admin_Item> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin__header_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     Admin_Item item = moviesList.get(position);
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