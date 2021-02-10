package com.androidwidget.formatedittext.domain;

public class Currency {
    private final String format;
    private final String decimalSeparator;
    private final int decimals;

    public Currency(String format, String decimalSeparator, int decimals) {

        this.format = format;
        this.decimalSeparator = decimalSeparator;
        this.decimals = decimals;
    }

    public String getFormat() {
        return format;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public int getDecimals() {
        return decimals;
    }
}
