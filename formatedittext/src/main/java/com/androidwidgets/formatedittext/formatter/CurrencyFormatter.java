package com.androidwidgets.formatedittext.formatter;

import com.androidwidgets.formatedittext.domain.Currency;
import com.androidwidgets.formatedittext.utils.FormatTextWatcher;
import com.androidwidgets.formatedittext.utils.Result;

public class CurrencyFormatter implements FormatTextWatcher.Formatter {

    private Currency currency;

    public CurrencyFormatter(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String getFormat() {
        return currency.getFormat();
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
        String wholeNumberFormat = getFormat().substring(0, getFormat().indexOf(currency.getDecimalSeparator()));

        int index = input.length() - 1;
        int indexForFormat = wholeNumberFormat.length() - 1;
        while (index >= 0) {
            if ('#' == wholeNumberFormat.charAt(indexForFormat)) {
                formattedWholeAmount = input.charAt(index) + formattedWholeAmount;
                index--;
                indexForFormat--;
            } else {
                formattedWholeAmount = wholeNumberFormat.charAt(indexForFormat) + formattedWholeAmount;
                indexForFormat--;
            }
        }

        return formattedWholeAmount;
    }
}
