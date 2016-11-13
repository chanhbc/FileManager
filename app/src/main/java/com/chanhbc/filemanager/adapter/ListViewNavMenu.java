package com.chanhbc.filemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanhbc.filemanager.App;
import com.chanhbc.filemanager.R;
import com.chanhbc.filemanager.manager.MenuItem;

import java.util.ArrayList;

public class ListViewNavMenu extends BaseAdapter {
    private ArrayList<MenuItem> items;
    private LayoutInflater inflater;

    public ListViewNavMenu(ArrayList<MenuItem> items) {
        inflater = LayoutInflater.from(App.getContext());
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MenuItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_nav_menu, viewGroup, false);
            holder = new Holder();
            holder.ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        MenuItem item = items.get(position);
        holder.ivIcon.setBackgroundResource(item.getImage());
        holder.tvName.setText(item.getName());
        return view;
    }

    private class Holder {
        private ImageView ivIcon;
        private TextView tvName;


    }
}
