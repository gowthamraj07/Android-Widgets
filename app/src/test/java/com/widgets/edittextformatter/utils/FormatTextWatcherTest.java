package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
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
        String format = "----";
        when(formatter.getFormat()).thenReturn(format);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter);

        textWatcher.init();

        verify(editText).setText("");
        verify(editText).setSelection(0);
    }

    @Test
    public void shouldGetTheFormatAndSetMaxLengthForEditText_WithTheLengthOfTheFormatPlus1() {
        String format = "----";
        when(formatter.getFormat()).thenReturn(format);
        SpyFormatTextWatcher textWatcher = new SpyFormatTextWatcher(editText, formatter);

        textWatcher.init();

        verify(formatter).getFormat();
        assertEquals(format.length()+1, textWatcher.spyMaxLength);
    }

    @Test
    public void shouldFormatFinalInputFromUserWithCursorPosition_AndDisplayInSameEditText() {
        // Assign
        String userInput = "1234";
        String formattedUserInput = "12 34";
        int currentCursorPosition = 3;
        int formattedCursorPosition = 4;
        Result result = new Result(formattedUserInput, formattedCursorPosition);

        when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter);

        // Act
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        verify(formatter).format(userInput, currentCursorPosition);
        verify(editText).setText(formattedUserInput);
        verify(editText).setSelection(formattedCursorPosition);
    }


    private class SpyFormatTextWatcher extends FormatTextWatcher {
        public int spyMaxLength;

        SpyFormatTextWatcher(EditText editText, Formatter formatter) {
            super(editText, formatter);
        }

        @Override
        protected void setEditTextMaxLength(int maxLengthOfEditText) {
            super.setEditTextMaxLength(maxLengthOfEditText);
            spyMaxLength = maxLengthOfEditText;
        }
    }
}