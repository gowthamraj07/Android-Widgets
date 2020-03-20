package com.androidwidgets.listview.adapter;

import com.androidwidgets.listview.domains.ListItem;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SelectableAdapterTest {

    @Test
    public void shouldReturnSizeOfListItems() {
        ListItem item1 = mock(ListItem.class);
        ListItem item2 = mock(ListItem.class);

        SelectableAdapter<ListItem> adapter = new SelectableAdapter<>(Arrays.asList(item1, item2));

        assertEquals(2, adapter.getItemCount());
    }
}