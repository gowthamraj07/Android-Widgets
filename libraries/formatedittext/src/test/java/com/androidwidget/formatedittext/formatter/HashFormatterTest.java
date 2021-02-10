package com.androidwidget.formatedittext.formatter;

import com.androidwidget.formatedittext.utils.Result;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class HashFormatterTest {

    @Test
    @Parameters({
            "1,1 | 1s-ss-ssss,1",
            "11,2 | 11-ss-ssss,2",
            "111,3 | 11-1s-ssss,4",
            "1111,4 | 11-11-ssss, 5",
            "11111,5 | 11-11-1sss, 7",
            "111111,6 | 11-11-11ss, 8",
            "1111111,7 | 11-11-111s, 9",
            "11111111,8 | 11-11-1111, 10",
    })
    public void shouldFormatInputWithCursorPosition(String inputText, int cursorPosition, String expectedOutput, int formattedCursorPosition) {
        String format = "##-##-####";
        HashFormatter formatter = new HashFormatter(format);

        Result result = formatter.format(inputText, cursorPosition);

        String expectedOutputWithSpaces = expectedOutput.replaceAll("s", " ").trim();
        assertEquals(expectedOutputWithSpaces, result.getFormattedUserInput());
        assertEquals(formattedCursorPosition, result.getFormattedCursorPosition());
    }
}