package com.androidwidgets.formatedittext.utils;

import android.text.Editable;

import com.androidwidgets.formatedittext.formatter.DashFormatter;
import com.androidwidgets.formatedittext.widgets.FormatEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class FormatTextWatcherTest {

    private FormatEditText editText;
    private FormatTextWatcher.Formatter formatter;
    private String format;
    private FormatTextWatcher.Validator validator;
    private FormatTextWatcher.ValidationListener listener;

    @Before
    public void setUp() {
        editText = mock(FormatEditText.class);
        formatter = mock(FormatTextWatcher.Formatter.class);
        validator = mock(FormatTextWatcher.Validator.class);
        listener = mock(FormatTextWatcher.ValidationListener.class);

        format = "----";
        when(formatter.getFormat()).thenReturn(format);
    }

    @Test
    public void shouldDisplayNothingAndCursorShouldBeAt0_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.init();

        verify(editText).setText("");
        verify(editText).setSelection(0);
    }

    @Test
    public void shouldDisplayFormatAsHint_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.init();

        verify(editText).setHint(format);
    }

    @Test
    public void shouldGetTheFormatAndSetMaxLengthForEditText_WithTheLengthOfTheFormatPlus1() {
        SpyFormatTextWatcher textWatcher = new SpyFormatTextWatcher();

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
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

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
        String format = "-- --";

        int currentCursorPosition = 3;
        int formattedCursorPosition = 4;
        Result result = new Result(formattedUserInput, formattedCursorPosition);

        when(formatter.getFormat()).thenReturn(format);
        when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        when(formatter.removeFormat(formattedUserInput)).thenReturn(userInput);
        when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        when(validator.validate(formattedUserInput, userInput)).thenReturn(true);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        verify(listener, times(0)).showError();
        verify(listener).showSuccess();
    }

    @Test
    @Parameters ({
        "$$--$$ | $$  $$, 2",
        "$--$ | $  $, 1",
    })
    public void shouldPlaceTheCursorAtFirstPossiblePosition(String format, String initialText, int expectedCursorPosition) {
        when(formatter.getFormat()).thenReturn(format);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.setInitialText();

        verify(editText).setText(initialText);
    }

    @Test
    @Parameters ({
            "$$ -- $$, | $$    $$",
            "$$ -- $$, $$ 1  $$| $$ 1  $$"
    })
    public void shouldSetFormatWithoutDashesAsInitialTextWhenNoInputIsGiven(String format, String input, String expectedResult) {
        when(formatter.getFormat()).thenReturn(format);
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn(input);
        when(editText.getText()).thenReturn(editable);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.setInitialTextWhenEmpty();

        verify(editText).setText(expectedResult);
    }

    @Test
    @Parameters ({
            "$$ -- $$, | $$    $$, ",
            "$$ -- $$, $$ 1  $$| $$ 1  $$, 1",
            "$$ -- $$, $$ 111 $$| $$ 11 $$, 11"
    })
    public void shouldPassFormattedInputToValidationListenerMethods(String format, String formattedInput, String expectedFormattedInput, String expectedUnformattedInput) {
        formatter = new DashFormatter(format);
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn(formattedInput);
        when(editText.getText()).thenReturn(editable);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.afterTextChanged(editable);

        verify(validator).validate(expectedFormattedInput, expectedUnformattedInput);
    }

    @Test
    public void shouldStopCallingOnSelectionWhenUpdatingTheTextViaCode_AndResetItAgainForUserInput() {
        // Assign
        String userInput = "1234";
        String formattedUserInput = "12 34";
        int currentCursorPosition = 3;
        int formattedCursorPosition = 4;
        Result result = new Result(formattedUserInput, formattedCursorPosition);

        when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = mock(Editable.class);
        when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        verify(formatter).format(userInput, currentCursorPosition);
        verify(editText).disableOnSelectionChange();
        verify(editText).setText(formattedUserInput);
        verify(editText).setSelection(formattedCursorPosition);
        verify(editText).enableOnSelectionChange();
    }

    private class SpyFormatTextWatcher extends FormatTextWatcher {
        int spyMaxLength;

        SpyFormatTextWatcher() {
            super(editText, formatter, validator, listener);
        }

        @Override
        protected void setEditTextMaxLength(int maxLengthOfEditText) {
            super.setEditTextMaxLength(maxLengthOfEditText);
            spyMaxLength = maxLengthOfEditText;
        }
    }
}