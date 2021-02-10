package com.androidwidget.formatedittext.formatter;

public class HashFormatter extends DashFormatter {
    public HashFormatter(String format) {
        super(format);
    }

    @Override
    public char getMaskCharacter() {
        return '#';
    }
}
