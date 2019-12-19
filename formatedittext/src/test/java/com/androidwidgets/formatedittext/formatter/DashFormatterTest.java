package com.androidwidgets.formatedittext.formatter;

import com.androidwidgets.formatedittext.utils.Result;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class DashFormatterTest {

    @Test
    @Parameters({
            "1,1 | 1,1",
            "111,1 | 11 1,1",
            "11 1,1 | 11 1,1",
            "11s,1 | 11, 1"
    })
    public void shouldReturnFormattedOutput(String input, int currentCursorPosition, String output, int nextCursorPosition) {
        DashFormatter formatter = new DashFormatter("-- --");

        Result result = formatter.format(input.replaceAll("s"," "), currentCursorPosition);

        assertEquals(output, result.getFormattedUserInput());
        assertEquals(nextCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters({
            "1,1 | 1,1",
            "111,3 | 11 1,4",
            "11 1,4 | 11 1,4",
            "11 11s, 6 | 11 11, 5"
    })
    public void shouldReturnNextCursorPosition_AfterFormatting(String input, int currentCursorPosition, String output, int nextCursorPosition) {
        DashFormatter formatter = new DashFormatter("-- -- --");

        Result result = formatter.format(input.replaceAll("s"," "), currentCursorPosition);

        assertEquals(output, result.getFormattedUserInput());
        assertEquals(nextCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters({
            "1, 1 | $$ 1, 4",
            "1234, 1 | $$ 12 34, 4",
            "$$ 12, 5 | $$ 12, 5",
            "$$ 123, 6 | $$ 12 3, 7",
            "$$ 1, 3 | $$ 1, 3"
    })
    public void shouldReturnFormattedOutputAndCursorAtRightPosition_WhenFormatNotStartingWithDash(String input, int currentCursorPosition, String output, int nextCursorPosition) {
        DashFormatter formatter = new DashFormatter("$$ -- --");

        Result result = formatter.format(input.replaceAll("s"," "), currentCursorPosition);

        assertEquals(output, result.getFormattedUserInput());
        assertEquals(nextCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters({
            "1, 1 | $$ 1  $$, 4",
            "$$ 12  $$, 4 | $$ 12 $$, 4"
    })
    public void shouldReturnFormattedOutputAndCursorAtRightPosition_WhenFormatNotStartingAndEndingWithDash(String input, int currentCursorPosition, String output, int nextCursorPosition) {
        DashFormatter formatter = new DashFormatter("$$ -- $$");

        Result result = formatter.format(input.replaceAll("s"," "), currentCursorPosition);

        assertEquals(output, result.getFormattedUserInput());
        assertEquals(nextCursorPosition, result.getFormattedCursorPosition());
    }

    @Ignore ("Need more analysis")
    @Test
    @Parameters({
            "$$ -- $$, $$    $$"
    })
    public void shouldReturnEmptyString_whenInputContainsOnlyFormat(String format, String input) {
        DashFormatter formatter = new DashFormatter(format);

        Result result = formatter.format(input, 0);

        assertEquals("", result.getFormattedUserInput());
    }

    @Test
    @Parameters({
            "$$--$$, $$  $$, 0 | 2",
            "$$ -- $$, $$   $$, 3 | 3"
    })
    public void shouldReturnCursorPositionOnlyInPossiblePositions(String format, String input, int initialCursorPosition, int expectedCursorPosition) {
        DashFormatter formatter = new DashFormatter(format);

        Result result = formatter.format(input, initialCursorPosition);

        assertEquals(expectedCursorPosition, result.getFormattedCursorPosition());
        assertTrue(result.getFormattedCursorPosition() < result.getFormattedUserInput().length());
    }

    @Test
    @Parameters({
            "$$ -- $$, $$ 1  $$ | 1",
            "+91 ----- -----, +91  91 | 91"
    })
    public void shouldRemoveFormat(String format, String text, String unformattedText) {
        DashFormatter formatter = new DashFormatter(format);

        String result = formatter.removeFormat(text);

        assertEquals(unformattedText, result);
    }
}