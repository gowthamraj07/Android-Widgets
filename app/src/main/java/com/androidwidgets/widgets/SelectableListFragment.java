package com.androidwidgets.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidwidgets.listview.SelectableRecyclerView;
import com.androidwidgets.listview.adapter.SelectableAdapter;
import com.androidwidgets.listview.domains.ListItem;
import com.androidwidgets.widgets.view.domains.SelectableListItem;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    private List<ListItem> getListItems() {
        LayoutInflater layoutInflater = getLayoutInflater();
        ListItem item1 = new SelectableListItem("Item 1", layoutInflater);
        ListItem item2 = new SelectableListItem("Item 2", layoutInflater);
        ListItem item3 = new SelectableListItem("Item 3", layoutInflater);
        ListItem item4 = new SelectableListItem("Item 4", layoutInflater);
        return Arrays.asList(item1, item2, item3, item4);
    }

}
