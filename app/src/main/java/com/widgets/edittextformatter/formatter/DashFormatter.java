package com.widgets.edittextformatter.formatter;

import com.widgets.edittextformatter.utils.FormatTextWatcher;
import com.widgets.edittextformatter.utils.MyResult;

public class DashFormatter implements FormatTextWatcher.Formatter {
    private String format;

    public DashFormatter(String format) {
        this.format = format;
    }

    @Override
    public MyResult format(String input, int currentCursorPosition) {
        MyResult result = new MyResult(input, currentCursorPosition);
        return result;
    }
}
