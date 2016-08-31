package com.pies.platform.viewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pies.platform.R;
import com.pies.platform.admin.model.Add_Home_item;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter {
    Context context;
    int flags[];
    String[] countryNames;
    LayoutInflater inflter;
    private ArrayList<String> key;
    private ArrayList<String> names;
    public MyCustomAdapter(Context applicationContext, ArrayList<String> names, ArrayList<String> key) {
        this.context = applicationContext;
        this.names = names;
        this.key = key;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return key.size();
    }

    @Override
    public Object getItem(int i) {
        return key.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_sub_context, null);
        TextView icon = (TextView) view.findViewById(R.id.textView3);
        TextView nameH = (TextView) view.findViewById(R.id.cust_view);

        nameH.setText(names.get(i));
        icon.setText(key.get(i));
        return view;
    }
}