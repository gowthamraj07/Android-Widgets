package com.androidwidgets.listview.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidwidgets.listview.domains.ListItem;

import java.util.List;

public class SelectableAdapter<T extends ListItem> extends RecyclerView.Adapter {
    private List<T> list;

    public SelectableAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
