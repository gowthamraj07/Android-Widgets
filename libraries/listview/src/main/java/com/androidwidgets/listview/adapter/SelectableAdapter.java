package com.androidwidgets.listview.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidwidgets.listview.domains.ListItem;

import java.util.List;

public class SelectableAdapter extends RecyclerView.Adapter {
    private final List<ListItem> list;

    public SelectableAdapter(List<ListItem> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return list.get(0).getViewHolder();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        list.get(position).bindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
