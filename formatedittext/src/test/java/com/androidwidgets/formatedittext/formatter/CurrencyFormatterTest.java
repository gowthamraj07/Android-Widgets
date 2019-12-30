package com.androidwidgets.formatedittext.formatter;

import com.androidwidgets.formatedittext.domain.Currency;
import com.androidwidgets.formatedittext.utils.Result;

import org.junit.Before;
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
            "1234 | 1\\,234.00"
    })
    public void shouldFormatInput(String amount, String expectedFormattedAmount) {
        CurrencyFormatter formatter = new CurrencyFormatter(aCurrency);

        Result formattedResult = formatter.format(amount, 0);

        assertEquals(expectedFormattedAmount, formattedResult.getFormattedUserInput());
    }
}