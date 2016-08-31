package com.pies.platform.viewHolder;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.admin.model.Add_Home_item;
import com.pies.platform.admin.model.Admin_Item;
import com.pies.platform.teachersActivity.MyHome;
import com.pies.platform.teachersActivity.model.Teacher_assign_homes;
import com.pies.platform.teachersActivity.model.teacher_data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nsikak  Thompson on 8/8/2016.
 */
public class Teacher_Assigned_Home extends RecyclerView.Adapter<Teacher_Assigned_Home.MyViewHolder> {


    public ImageView thumnial;
    private ArrayList<Add_Home_item> moviesList;
    LocationManager locationManager;
    Criteria criteria;
    Location location;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hname;
        public TextView haddress, distance, header;

        public MyViewHolder(View view) {
            super(view);

            hname = (TextView) itemView.findViewById(R.id.head_text);
            haddress = (TextView) itemView.findViewById(R.id.address);
            //thumnial = (ImageView) itemView.findViewById(R.id.teacher_image_list);
            distance = (TextView) itemView.findViewById(R.id.distance);
            header = (TextView) itemView.findViewById(R.id.header);
        }
    }

    public Teacher_Assigned_Home(ArrayList<Add_Home_item> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_obj_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Add_Home_item item = moviesList.get(position);

        if(MyHome.lat != 0 && MyHome.longt != 0){
            double lat2 = item.getLatitude();
            double longt2 = item.getLongitude();

            double theta = MyHome.longt - longt2;
            double dist = Math.sin(deg2rad(MyHome.lat)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(MyHome.lat)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            int dis = (int) (dist * 1.609344);

            String d = String.valueOf(dis + "km Away");
            // viewHolder.distance.setText(d);
            holder.distance.setText(d);
        }


        holder.hname.setText(item.getName());
        holder.haddress.setText(item.getAddress());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


/*
    public void bindToPost(Add_Home_item post, View.OnClickListener starClickListener) {
        *//*if(post.getName() != null){
            String n = post.getName();
            String first = n.substring(0,1);
            index.setText(first);
        }*//*

      hname.setText(post.getName());
        haddress.setText(post.getAddress());

        double lat = post.getLatitude();
        double longt = post.getLongitude();

       // header.setText(post.getHeader());
       // distance.setText(post.getHome_kilo());


        //starView.setOnClickListener(starClickListener);
    }*/

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
