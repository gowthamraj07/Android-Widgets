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
        String formatWithoutDecimalSeparator = getFormat().replaceAll("\\" + currency.getDecimalSeparator(), "");
        String unformattedInput = userInput;
        for (char ch : formatWithoutDecimalSeparator.toCharArray()) {
            unformattedInput = unformattedInput.replaceAll("\\" + ch, "");
        }

        return unformattedInput;
    }

    @Override
    public Result format(String input, int currentCursorPosition) {
        String formattedAmount = getFormattedWholeNumber(input) + ".00";
        return new Result(formattedAmount, currentCursorPosition);
    }

    private String getFormattedWholeNumber(final String input) {
        int positionOfDecimalSeparator = getFormat().indexOf(currency.getDecimalSeparator());
        String wholeNumberFormat = getFormat().substring(0, positionOfDecimalSeparator);

        int index = input.length() - 1;
        int indexForFormat = wholeNumberFormat.length() - 1;
        String formattedWholeAmount = "";

        while (hasMoreCharactersToFormat(index)) {
            if (isIndexBeyondFormatLength(indexForFormat)) {
                indexForFormat = getPreviousSimilarFormatIndex(wholeNumberFormat);
            }

            if (canPlaceNumberInFormat(wholeNumberFormat, indexForFormat)) {
                formattedWholeAmount = placeNumberInOutput(input, formattedWholeAmount, index);
                index--;
                indexForFormat--;
            } else {
                formattedWholeAmount = placeCharacterFromFormatInOutput(formattedWholeAmount, wholeNumberFormat, indexForFormat);
                indexForFormat--;
            }
        }

        return formattedWholeAmount;
    }

    private String placeCharacterFromFormatInOutput(String formattedWholeAmount, String wholeNumberFormat, int indexForFormat) {
        return wholeNumberFormat.charAt(indexForFormat) + formattedWholeAmount;
    }

    private String placeNumberInOutput(String input, String formattedWholeAmount, int index) {
        return input.charAt(index) + formattedWholeAmount;
    }

    private boolean canPlaceNumberInFormat(String wholeNumberFormat, int indexForFormat) {
        return '#' == wholeNumberFormat.charAt(indexForFormat);
    }

    private int getPreviousSimilarFormatIndex(String wholeNumberFormat) {
        char separator = wholeNumberFormat.charAt(1);
        return wholeNumberFormat.indexOf(separator, 2) - 2;
    }

    private boolean isIndexBeyondFormatLength(int indexForFormat) {
        return indexForFormat < 0;
    }

    private boolean hasMoreCharactersToFormat(int index) {
        return index >= 0;
    }
}
