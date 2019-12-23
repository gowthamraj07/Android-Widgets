package com.androidwidgets.formatedittext.utils;

import android.text.Editable;

import com.androidwidgets.formatedittext.formatter.DashFormatter;
import com.androidwidgets.formatedittext.widgets.FormatEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class FormatTextWatcherTest {

    private FormatEditText editText;
    private FormatTextWatcher.Formatter formatter;
    private String format;
    private FormatTextWatcher.Validator validator;
    private FormatTextWatcher.ValidationListener listener;

    @Before
    public void setUp() {
        editText = Mockito.mock(FormatEditText.class);
        formatter = Mockito.mock(FormatTextWatcher.Formatter.class);
        validator = Mockito.mock(FormatTextWatcher.Validator.class);
        listener = Mockito.mock(FormatTextWatcher.ValidationListener.class);

        format = "----";
        Mockito.when(formatter.getFormat()).thenReturn(format);
    }

    @Test
    public void shouldDisplayNothingAndCursorShouldBeAt0_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.init();

        Mockito.verify(editText).setSelection(0);
    }

    @Test
    public void shouldDisplayFormatAsHint_whenInitialized() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.init();

        Mockito.verify(editText).setHint(format);
    }

    @Test
    public void shouldGetTheFormatAndSetMaxLengthForEditText_WithTheLengthOfTheFormatPlus1() {
        SpyFormatTextWatcher textWatcher = new SpyFormatTextWatcher();

        textWatcher.init();

        Mockito.verify(formatter).getFormat();
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

        Mockito.when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        Mockito.when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        Mockito.verify(formatter).format(userInput, currentCursorPosition);
        Mockito.verify(editText).setText(formattedUserInput);
        Mockito.verify(editText).setSelection(formattedCursorPosition);
    }

    @Test
    public void shouldInitializeInputLayoutWithEmpty_whenValidationListenerAndValidatorAreGiven() {
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.init();

        Mockito.verify(listener).showEmpty();
    }

    @Test
    public void shouldShowError_whenValidationFails() {
        // Assign
        String userInput = "1234";
        String formattedUserInput = "12 34";

        int currentCursorPosition = 3;
        int formattedCursorPosition = 4;
        Result result = new Result(formattedUserInput, formattedCursorPosition);

        Mockito.when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        Mockito.when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        Mockito.verify(listener).showError();
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

        Mockito.when(formatter.getFormat()).thenReturn(format);
        Mockito.when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        Mockito.when(formatter.removeFormat(formattedUserInput)).thenReturn(userInput);
        Mockito.when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        Mockito.when(validator.validate(formattedUserInput, userInput)).thenReturn(true);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        Mockito.verify(listener, Mockito.times(0)).showError();
        Mockito.verify(listener).showSuccess();
    }

    @Test
    @Parameters ({
        "$$--$$ | $$  $$, 2",
        "$--$ | $  $, 1",
    })
    public void shouldPlaceTheCursorAtFirstPossiblePosition(String format, String initialText, int expectedCursorPosition) {
        Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn("");
        Mockito.when(editText.getText()).thenReturn(editable);
        Mockito.when(formatter.getFormat()).thenReturn(format);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.setInitialTextWhenEmpty();

        Mockito.verify(editText).setText(initialText);
    }

    @Test
    @Parameters ({
            "$$ -- $$, | $$    $$",
            "$$ -- $$, $$ 1  $$| $$ 1  $$"
    })
    public void shouldSetFormatWithoutDashesAsInitialTextWhenNoInputIsGiven(String format, String input, String expectedResult) {
        Mockito.when(formatter.getFormat()).thenReturn(format);
        Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn(input);
        Mockito.when(editText.getText()).thenReturn(editable);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.setInitialTextWhenEmpty();

        Mockito.verify(editText).setText(expectedResult);
    }

    @Test
    @Parameters ({
            "$$ -- $$, | $$    $$, ",
            "$$ -- $$, $$ 1  $$| $$ 1  $$, 1",
            "$$ -- $$, $$ 111 $$| $$ 11 $$, 11"
    })
    public void shouldPassFormattedInputToValidationListenerMethods(String format, String formattedInput, String expectedFormattedInput, String expectedUnformattedInput) {
        formatter = new DashFormatter(format);
        Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn(formattedInput);
        Mockito.when(editText.getText()).thenReturn(editable);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        textWatcher.afterTextChanged(editable);

        Mockito.verify(validator).validate(expectedFormattedInput, expectedUnformattedInput);
    }

    @Test
    public void shouldStopCallingOnSelectionWhenUpdatingTheTextViaCode_AndResetItAgainForUserInput() {
        // Assign
        String userInput = "1234";
        String formattedUserInput = "12 34";
        int currentCursorPosition = 3;
        int formattedCursorPosition = 4;
        Result result = new Result(formattedUserInput, formattedCursorPosition);

        Mockito.when(editText.getSelectionStart()).thenReturn(currentCursorPosition);
        Mockito.when(formatter.format(userInput, currentCursorPosition)).thenReturn(result);
        FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, validator, listener);

        // Act
        Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn(userInput);
        textWatcher.afterTextChanged(editable);

        // Assert
        Mockito.verify(formatter).format(userInput, currentCursorPosition);
        Mockito.verify(editText).disableOnSelectionChange();
        Mockito.verify(editText).setText(formattedUserInput);
        Mockito.verify(editText).setSelection(formattedCursorPosition);
        Mockito.verify(editText).enableOnSelectionChange();
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