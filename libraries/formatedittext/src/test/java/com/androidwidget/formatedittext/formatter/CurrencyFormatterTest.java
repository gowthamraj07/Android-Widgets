package com.androidwidget.formatedittext.formatter;

import com.androidwidget.formatedittext.domain.Currency;
import com.androidwidget.formatedittext.utils.Result;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class CurrencyFormatterTest {

    private Currency aCurrency;

    @Before
    public void setUp() {
        aCurrency = new Currency("#,##,###.##", ".", 2);
    }

    @Test
    public void shouldReturnFormat() {
        String format = "#,##,###.##";
        aCurrency = new Currency(format, ".", 2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        assertEquals(format, formatter.getFormat());
    }

    @Test
    @Parameters ({
            "1 | 1.00",
            "1234 | 1\\,234.00",
            "12345 | 12\\,345.00",
            "123456 | 1\\,23\\,456.00",
            "1234567 | 12\\,34\\,567.00",
            "12345678 | 1\\,23\\,45\\,678.00",
            "123456789 | 12\\,34\\,56\\,789.00",
    })
    public void shouldFormatInput(String amount, String expectedFormattedAmount) {
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result formattedResult = formatter.format(amount, 0);

        assertEquals(expectedFormattedAmount, formattedResult.getFormattedUserInput());
    }

    @Test
    @Parameters ({
            "1 | 1.00",
            "12 | 12.00",
            "123 | 123.00",
            "1234 | 1\\,234.00",
            "12345 | 12\\,345.00",
            "123456 | 123\\,456.00",
            "1234567 | 1\\,234\\,567.00",
            "12345678 | 12\\,345\\,678.00",
            "123456789 | 123\\,456\\,789.00",
    })
    public void shouldFormatInputForFormat_US(String amount, String expectedFormattedAmount) {
        aCurrency = new Currency("#,###,###.##",".",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result formattedResult = formatter.format(amount, 0);

        assertEquals(expectedFormattedAmount, formattedResult.getFormattedUserInput());
    }

    @Test
    @Parameters({
            "#\\,###\\,###.##, 1\\,234.00 | 1234.00"
    })
    public void shouldRemoveFormatFromInput(String format, String formattedInput, String expectedOutput) {
        aCurrency = new Currency(format,".",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        String unformattedInput = formatter.removeFormat(formattedInput);

        assertEquals(expectedOutput, unformattedInput);
    }

    @Test
    @Parameters ({
            "1 , 1| 1",
            "12 , 2| 2",
            "123 , 3| 3",
            "1234 , 4| 5",
            "1234 , 1| 1",
    })
    public void shouldReturnCursorPositionAfterFormattingInput(String input, int currentCursorPosition, int formattedCursorPosition) {
        aCurrency = new Currency("#,##,###.##",".",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result result = formatter.format(input, currentCursorPosition);

        assertEquals(formattedCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters ({
            "1..00 , 2| 1.00, 2"
    })
    public void shouldPlaceCursorAfterDecimalSeparatorInFormattedInput(String input, int currentCursorPosition, String formattedOutput, int formattedCursorPosition) {
        aCurrency = new Currency("#,##,###.##",".",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result result = formatter.format(input, currentCursorPosition);

        assertEquals(formattedOutput, result.getFormattedUserInput());
        assertEquals(formattedCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters ({
            "1..00 , 2| 1.00, 2"
    })
    public void shouldMoveCursorToNextPositionWhenUserInputsDecimalSeparator(String input, int currentCursorPosition, String formattedOutput, int formattedCursorPosition) {
        aCurrency = new Currency("#,##,###.##",".",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result result = formatter.format(input, currentCursorPosition);

        assertEquals(formattedOutput, result.getFormattedUserInput());
        assertEquals(formattedCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters ({
            "0.100, 3 | 0.10, 3",
            "0.110, 4 | 0.11, 4",
            "0.119, 5 | 0.11, 4",
            "0.11, 4 | 0.11, 4",
            "0.1, 3 | 0.10, 3",
    })
    public void shouldFormatDecimalNumber(String input, int cursorPosition, String formattedOutput, int formattedCursorPosition) {
        aCurrency = new Currency("#,##,###.##",".",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result result = formatter.format(input, cursorPosition);

        assertEquals(formattedOutput, result.getFormattedUserInput());
        assertEquals(formattedCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters ({
            "0\\,100, 3 | 0\\,10, 3",
            "0\\,110, 4 | 0\\,11, 4",
            "0\\,119, 5 | 0\\,11, 4",
            "0\\,11, 4  | 0\\,11, 4",
            "0\\,1, 3   | 0\\,10, 3",
    })
    public void shouldFormatDecimalNumber_forGermanFormat(String input, int cursorPosition, String formattedOutput, int formattedCursorPosition) {
        aCurrency = new Currency("# ### ###,##",",",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result result = formatter.format(input, cursorPosition);

        assertEquals(formattedOutput, result.getFormattedUserInput());
        assertEquals(formattedCursorPosition, result.getFormattedCursorPosition());
    }

    @Test
    @Parameters ({
            "1 | 1\\,00",
            "12 | 12\\,00",
            "123 | 123\\,00",
            "1234 | 1 234\\,00",
            "12345 | 12 345\\,00",
            "123456 | 123 456\\,00",
            "1234567 | 1 234 567\\,00",
    })
    public void shouldFormatInput_forGermanCurrency(String amount, String expectedFormattedAmount) {
        aCurrency = new Currency("# ### ###,##",",",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result formattedResult = formatter.format(amount, 0);

        assertEquals(expectedFormattedAmount, formattedResult.getFormattedUserInput());
    }

    @Test
    @Ignore
    @Parameters ({
            "0100, 2 | 0\\,10, 1",
            "0110, 2 | 0\\,11, 1",
            "0119, 2 | 0\\,11, 1",
            "011, 2  | 0\\,11, 1",
            "01, 2   | 0\\,10, 1",
    })
    public void shouldMaintainFormattedOutput_withCursorPositionMinus1_WhenUserDeletesTheDecimalPlace(String input, int cursorPosition, String formattedOutput, int formattedCursorPosition) {
        aCurrency = new Currency("# ### ###,##",",",2);
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result formattedResult = formatter.format(input, cursorPosition);

        assertEquals(formattedOutput, formattedResult.getFormattedUserInput());
        assertEquals(formattedCursorPosition, formattedResult.getFormattedCursorPosition());
    }
}