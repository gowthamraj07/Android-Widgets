package com.androidwidgets.listview.adapter;

import android.view.ViewGroup;

import com.androidwidgets.listview.domains.ListItem;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import androidx.recyclerview.widget.RecyclerView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SelectableAdapterTest {

    @Test
    public void shouldReturnSizeOfListItems() {
        ListItem item1 = mock(ListItem.class);
        ListItem item2 = mock(ListItem.class);

        SelectableAdapter adapter = new SelectableAdapter(Arrays.asList(item1, item2));

        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void shouldCallOnCreateViewHolderOfListItem_WhenOnCreateViewHolderOfAdapterIsCalled() {
        ListItem mockItem = mock(ListItem.class);
        SelectableAdapter adapter = new SelectableAdapter(Collections.singletonList(mockItem));

        ViewGroup parent = mock(ViewGroup.class);
        int viewType = 0;
        adapter.onCreateViewHolder(parent, viewType);

        verify(mockItem).getViewHolder();
    }

    @Test
    public void shouldCallOnBindViewHolderOfListItem_WhenOnBindViewHolderOfAdapterIsCalled() {
        ListItem mockItem = mock(ListItem.class);
        SelectableAdapter adapter = new SelectableAdapter(Collections.singletonList(mockItem));

        RecyclerView.ViewHolder viewHolder = mock(RecyclerView.ViewHolder.class);
        int position = 0;
        adapter.onBindViewHolder(viewHolder, position);

        verify(mockItem).bindView(viewHolder, position);
    }
}