package com.android.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.widgets.view.domains.SimpleTextViewHolder;
import com.androidwidgets.listview.SelectableRecyclerView;
import com.androidwidgets.listview.adapter.SelectableAdapter;
import com.androidwidgets.listview.domains.ListItem;

import java.util.Arrays;
import java.util.List;

public class SelectableListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selectable_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SelectableRecyclerView recyclerView = view.findViewById(R.id.selectable_recycler_view);
        RecyclerView.Adapter adapter = new SelectableAdapter(getListItems());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<ListItem> getListItems() {
        ListItem item1 = new SelectableListItem("Item 1");
        ListItem item2 = new SelectableListItem("Item 2");
        ListItem item3 = new SelectableListItem("Item 3");
        ListItem item4 = new SelectableListItem("Item 4");
        return Arrays.asList(item1, item2, item3, item4);
    }

    private class SelectableListItem implements ListItem {
        private String text;

        SelectableListItem(String text) {
            this.text = text;
        }

        @Override
        public RecyclerView.ViewHolder getViewHolder() {
            View view = getLayoutInflater().inflate(android.R.layout.activity_list_item, null);
            return new SimpleTextViewHolder(view);
        }

        @Override
        public void bindView(RecyclerView.ViewHolder viewHolder, int position) {
            ((SimpleTextViewHolder) viewHolder).getTextView().setText(text);
        }
    }
}
