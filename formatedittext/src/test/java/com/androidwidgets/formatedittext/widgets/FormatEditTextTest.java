package com.androidwidgets.formatedittext.widgets;

import android.text.InputFilter;

import com.androidwidgets.formatedittext.presenter.FormatEditTextPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class FormatEditTextTest {

    @Test
    @Parameters({
            "$$ -- $$, $$ -- $$, 0 | 3",
            "$$ -- $$, , 4 | 0"
    })
    public void shouldReturnFirstPossibleCursorPosition(String format, String input, int startSelection, int expectedCursorPosition) {
        int newSelectionStart = new FormatEditTextPresenter().getStartSelection(startSelection, format, input);

        assertEquals(expectedCursorPosition, newSelectionStart);
    }

    @Test
    @Parameters({
            "$$ -- $$, $$ -- $$, 4 | -1"
    })
    public void shouldReturnMinus1_whenCursorIsInValidPosition(String format, String input, int startSelection, int expectedCursorPosition) {
        int newSelectionStart = new FormatEditTextPresenter().getStartSelection(startSelection, format, input);
        int newSelectionEnd = new FormatEditTextPresenter().getLastSelection(startSelection, format, input);

        assertEquals(expectedCursorPosition, newSelectionStart);
        assertEquals(expectedCursorPosition, newSelectionEnd);
    }

    @Test
    @Parameters({
            "$$ -- $$, $$ -- $$, 7 | 5",
            "$$ -- $$, , 4 | 0"
    })
    public void shouldReturnLastPossibleCursorPosition(String format, String input, int startSelection, int expectedCursorPosition) {
        int newSelectionStart = new FormatEditTextPresenter().getLastSelection(startSelection, format, input);

        assertEquals(expectedCursorPosition, newSelectionStart);
    }

    @Test
    public void shouldAddGivenInputFilterToExistingFilters() {
        InputFilter[] existingFilters = new InputFilter[0];
        InputFilter newFilter = Mockito.mock(InputFilter.class);

        InputFilter[] resultFilters = new FormatEditTextPresenter().addInputFilterTo(existingFilters, newFilter);

        assertEquals(existingFilters.length + 1, resultFilters.length);
    }

    @Test
    public void shouldRemoveGivenInputFilterFromExistingFilters() {
        InputFilter[] existingFilters = new InputFilter[1];
        InputFilter newFilter = Mockito.mock(InputFilter.class);
        existingFilters[0] = newFilter;

        InputFilter[] resultFilters = new FormatEditTextPresenter().removeInputFilterTo(existingFilters, newFilter);

        assertEquals(existingFilters.length - 1, resultFilters.length);
    }
}