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

        String resultString = format;
        for (char ch : input.toCharArray()) {
            resultString = resultString.replaceFirst("\\-", "" + ch);
        }

        resultString = resultString.replaceAll("\\-"," ");
        resultString = resultString.trim();

        return new Result(resultString, currentCursorPosition);
    }
}
