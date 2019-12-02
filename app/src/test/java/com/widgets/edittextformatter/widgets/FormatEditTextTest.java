package com.widgets.edittextformatter.widgets;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class FormatEditTextTest {

    @Test
    @Parameters({
            "$$ -- $$, 0 | 3",
            "$$ -- $$, 4 | 4",
    })
    public void shouldReturnFirstPossibleCursorPosition(String format, int startSelection, int expectedCursorPosition) {
        int newSelectionStart = FormatEditText.getStartSelection(startSelection, format);

        assertEquals(expectedCursorPosition, newSelectionStart);
    }

    @Test
    @Parameters({
            "$$ -- $$, 7 | 5"
    })
    public void shouldReturnLastPossibleCursorPosition(String format, int startSelection, int expectedCursorPosition) {
        int newSelectionStart = FormatEditText.getLastSelection(startSelection, format);

        assertEquals(expectedCursorPosition, newSelectionStart);
    }
}