package com.androidwidgets.formatedittext.formatter;

import com.androidwidgets.formatedittext.utils.Result;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class CurrencyFormatterTest {

    @Test
    @Parameters ({
        "#\\,##\\,###.##" // #,##,###.##
    })
    public void shouldReturnFormat(String format) {
        CurrencyFormatter formatter = new CurrencyFormatter(format);

        assertEquals(format, formatter.getFormat());
    }

    @Test
    @Parameters ({
            "1 | 1.00"
    })
    public void shouldFormatInput(String amount, String expectedFormattedAmount) {
        CurrencyFormatter formatter = new CurrencyFormatter("#,##,###.##");

        Result formattedResult = formatter.format(amount, 0);

        assertEquals(expectedFormattedAmount, formattedResult.getFormattedUserInput());
    }
}