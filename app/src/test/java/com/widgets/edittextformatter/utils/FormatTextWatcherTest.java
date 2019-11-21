package com.widgets.edittextformatter.utils;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FormatTextWatcherTest {

    private EditText editText;

    @Before
    public void setUp() {
        editText = mock(EditText.class);
    }

    @Test
    public void shouldDisplayNothingAndCursorShouldBeAt0_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText);

        textWatcher.init();

        verify(editText).setText("");
        verify(editText).setSelection(0);
    }
}