package com.androidwidgets.widgets.view.domains;

import android.view.LayoutInflater;
import android.view.View;

import com.androidwidgets.listview.domains.ListItem;
import com.androidwidgets.widgets.R;

import androidx.recyclerview.widget.RecyclerView;

public class SelectableListItem implements ListItem {
    private String text;
    private LayoutInflater layoutInflater;

    public SelectableListItem(String text, LayoutInflater layoutInflater) {
        this.text = text;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        View view = layoutInflater.inflate(R.layout.layout_selectable_list_item, null);
        return new SimpleTextViewHolder(view);
    }

    @Override
    public void bindView(RecyclerView.ViewHolder viewHolder, int position) {
        ((SimpleTextViewHolder) viewHolder).getTextView().setText(text);
    }
}
