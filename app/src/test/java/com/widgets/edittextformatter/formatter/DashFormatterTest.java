package com.widgets.edittextformatter.formatter;

import com.widgets.edittextformatter.utils.Result;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

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
            "$$ 12, 5 | $$ 12, 5"
    })
    public void shouldReturnFormattedOutputAndCursorAtRightPosition_WhenFormatNotStartingWithDash(String input, int currentCursorPosition, String output, int nextCursorPosition) {
        DashFormatter formatter = new DashFormatter("$$ -- --");

        Result result = formatter.format(input.replaceAll("s"," "), currentCursorPosition);

        assertEquals(output, result.getFormattedUserInput());
        assertEquals(nextCursorPosition, result.getFormattedCursorPosition());
    }
}