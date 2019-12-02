package com.widgets.edittextformatter.widgets;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatEditTextTest {

    @Test
    public void shouldReturnFirstPossibleCursorPosition() {
        int startSelection = 0;
        String format = "$$ -- $$";

        int newSelectionStart = FormatEditText.getStartSelection(startSelection, format);

        assertEquals(3, newSelectionStart);
    }
}