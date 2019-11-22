package com.widgets.edittextformatter.formatter;

import com.widgets.edittextformatter.utils.FormatTextWatcher;
import com.widgets.edittextformatter.utils.Result;

public class DashFormatter implements FormatTextWatcher.Formatter {
    private String format;

    public DashFormatter(String format) {
        this.format = format;
    }

    @Override
    public Result format(final String input, int currentCursorPosition) {

        String unformattedInput = removeFormatFrom(input);

        String resultString = format;
        for (char ch : unformattedInput.toCharArray()) {
            resultString = resultString.replaceFirst("\\-", "" + ch);
        }

        resultString = resultString.replaceAll("\\-", " ");
        resultString = resultString.trim();

        return new Result(resultString, currentCursorPosition);
    }

    @Override
    public String getFormat() {
        return format;
    }

    private String removeFormatFrom(final String input) {
        String formatWithoutDashes = format.replaceAll("-", "");
        String unformattedInput = input;
        for (char ch : formatWithoutDashes.toCharArray()) {
            unformattedInput = unformattedInput.replaceAll("" + ch, "");
        }

        return unformattedInput.trim();
    }
}
