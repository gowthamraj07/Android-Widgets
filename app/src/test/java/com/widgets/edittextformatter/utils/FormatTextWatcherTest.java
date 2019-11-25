package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FormatTextWatcherTest {

    private EditText editText;
    private FormatTextWatcher.Formatter formatter;
    private String format;
    private FormatTextWatcher.Validator validator;
    private FormatTextWatcher.ValidationListener listener;

    @Before
    public void setUp() {
        editText = mock(EditText.class);
        formatter = mock(FormatTextWatcher.Formatter.class);
        validator = mock(FormatTextWatcher.Validator.class);
        listener = mock(FormatTextWatcher.ValidationListener.class);

        format = "----";
        when(formatter.getFormat()).thenReturn(format);
    }

    @Test
    public void shouldDisplayNothingAndCursorShouldBeAt0_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter);

        textWatcher.init();

        verify(editText).setText("");
        verify(editText).setSelection(0);
    }

    @Test
    public void shouldDisplayFormatAsHint_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter);

        textWatcher.init();

        verify(editText).setHint(format);
    }

    @Test
    public void shouldGetTheFormatAndSetMaxLengthForEditText_WithTheLengthOfTheFormatPlus1() {
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
        when(formatter.canAcceptMoreCharacters(null)).thenReturn(true);
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

    @Test
    public void shouldInitializeInputLayoutWithEmpty_whenValidationListenerAndValidatorAreGiven() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.init();

        verify(listener).showEmpty();
    }

    @Test
    public void shouldShowError_whenValidationFails() {
        // Assign
        String userInput = "1234";
        String formattedUserInput = "12 34";

        int currentCursorPosition = 3;
        int formattedCursorPosition = 4;
        Result result = new Result(formattedUserInput, formattedCursorPosition);

        when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        when(formatter.canAcceptMoreCharacters(null)).thenReturn(true);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        verify(listener).showError();
    }

    @Test
    public void shouldShowHint_whenValidationSuccess() {
        // Assign
        String userInput = "1234";
        String formattedUserInput = "12 34";

        int currentCursorPosition = 3;
        int formattedCursorPosition = 4;
        Result result = new Result(formattedUserInput, formattedCursorPosition);

        when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        when(formatter.canAcceptMoreCharacters(null)).thenReturn(true);
        when(validator.validate(formattedUserInput, userInput)).thenReturn(true);
        when(formatter.removeFormat(userInput)).thenReturn(userInput);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        verify(listener, times(0)).showError();
        verify(listener).showSuccess();
    }

    private class SpyFormatTextWatcher extends FormatTextWatcher {
        int spyMaxLength;

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