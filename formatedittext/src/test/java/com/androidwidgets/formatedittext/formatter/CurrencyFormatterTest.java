package com.androidwidgets.formatedittext.formatter;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class CurrencyFormatterTest {

    @Test
    @Parameters ({
        "#\\,##\\,###\\.##" // #,##,###.##
    })
    public void shouldReturnFormat(String format) {
        CurrencyFormatter formatter = new CurrencyFormatter(format);

        assertEquals(format, formatter.getFormat());
    }
}