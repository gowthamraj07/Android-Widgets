package com.widgets.edittextformatter.formatter;

import com.widgets.edittextformatter.utils.FormatTextWatcher;
import com.widgets.edittextformatter.utils.Result;

public class DashFormatter implements FormatTextWatcher.Formatter {
    private String format;

    public DashFormatter(String format) {
        this.format = format;
    }

    @Override
    public Result format(String input, int currentCursorPosition) {
        Result result = new Result(input, currentCursorPosition);
        return result;
    }
}
