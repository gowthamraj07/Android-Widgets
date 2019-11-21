package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FormatTextWatcherTest {

    private EditText editText;
    private FormatTextWatcher.Formatter formatter;

    @Before
    public void setUp() {
        editText = mock(EditText.class);
        formatter = mock(FormatTextWatcher.Formatter.class);
    }

    @Test
    public void shouldDisplayNothingAndCursorShouldBeAt0_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter);

        textWatcher.init();

        verify(editText).setText("");
        verify(editText).setSelection(0);
    }

    @Test
    public void shouldFormatFinalInputFromUser() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter);

        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn("1234");
        textWatcher.afterTextChanged(editable);

        verify(formatter).format("1234");
    }


}