package com.androidwidgets.formatedittext.formatter;

import com.androidwidgets.formatedittext.utils.FormatTextWatcher;
import com.androidwidgets.formatedittext.utils.Result;

public class CurrencyFormatter implements FormatTextWatcher.Formatter {
    private String format;

    public CurrencyFormatter(String format) {
        this.format = format;
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public String removeFormat(String userInput) {
        return null;
    }

    @Override
    public Result format(String input, int currentCursorPosition) {
        String formattedAmount = getFormattedWholeNumber(input) + ".00";
        return new Result(formattedAmount, 0);
    }

    private String getFormattedWholeNumber(final String input) {
        String formattedWholeAmount = "";
        if (input.length() > 3) {
            int thousandSeparatorLocation = input.length() - 3;
            formattedWholeAmount = input.substring(0, thousandSeparatorLocation) + "," + input.substring(thousandSeparatorLocation);
        } else {
            formattedWholeAmount = input;
        }

        return formattedWholeAmount;
    }
}
