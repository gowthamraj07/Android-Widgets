package com.androidwidgets.formatedittext.formatter;

import com.androidwidgets.formatedittext.domain.Currency;
import com.androidwidgets.formatedittext.utils.FormatTextWatcher;
import com.androidwidgets.formatedittext.utils.Result;

import java.text.DecimalFormat;

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

        input = removeNewCharacterIfDecimalSeparator(input, currentCursorPosition);

        int positionOfDecimalSeparator = input.indexOf(currency.getDecimalSeparator());
        String wholeNumber = getWholeNumber(input, positionOfDecimalSeparator);
        String decimalNumber = getDecimalNumber(input, positionOfDecimalSeparator);

        String formattedWholeNumber = getFormattedWholeNumber(removeFormat(wholeNumber));
        String formattedDecimalNumber = getFormattedDecimalNumber(decimalNumber);
        String formattedAmount = formattedWholeNumber + formattedDecimalNumber;

        int specialCharactersBeforeFormatting = getSpecialCharactersCountOfStringTillPosition(wholeNumber, currentCursorPosition);
        int specialCharactersAfterFormatting = getSpecialCharactersCountOfStringTillPosition(formattedWholeNumber, currentCursorPosition);
        int formattedCursorPosition = currentCursorPosition + (specialCharactersAfterFormatting - specialCharactersBeforeFormatting);
        formattedCursorPosition = formattedCursorPosition > formattedAmount.length() ? formattedAmount.length() : formattedCursorPosition;

        return new Result(formattedAmount, formattedCursorPosition);
    }

    private String getFormattedDecimalNumber(String decimalNumber) {
        int indexOfDecimalSeparator = decimalNumber.indexOf(currency.getDecimalSeparator());

        // Get valid decimal number for formatting
        String validDecimalNumber = getValidDecimalNumberFrom(decimalNumber, indexOfDecimalSeparator);
        validDecimalNumber = validDecimalNumber.replace(currency.getDecimalSeparator(), ".");

        // Convert to float value
        Float decimalAmount = Float.parseFloat(validDecimalNumber);

        // Setup formatting for decimal value
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        // Return formatted decimal value
        String formattedDecimalValue = decimalFormat.format(decimalAmount);
        formattedDecimalValue = formattedDecimalValue.replace(".", currency.getDecimalSeparator());
        return formattedDecimalValue.substring(formattedDecimalValue.indexOf(currency.getDecimalSeparator()));
    }

    private String getValidDecimalNumberFrom(String decimalNumber, int indexOfDecimalSeparator) {
        String decimalNumberWithoutDecimal = decimalNumber.substring(indexOfDecimalSeparator + 1);
        if (decimalNumberWithoutDecimal.length() > currency.getDecimals()) {
            return currency.getDecimalSeparator() + decimalNumberWithoutDecimal.substring(0, currency.getDecimals());
        }

        if (decimalNumberWithoutDecimal.length() == currency.getDecimals()) {
            return currency.getDecimalSeparator() + decimalNumberWithoutDecimal;
        }

        for (int index = decimalNumberWithoutDecimal.length(); index < currency.getDecimals(); index++) {
            decimalNumberWithoutDecimal += "0";
        }

        return currency.getDecimalSeparator() + decimalNumberWithoutDecimal;
    }

    private String removeNewCharacterIfDecimalSeparator(String input, int currentCursorPosition) {
        boolean isValidCursorPosition = (currentCursorPosition - 1) > -1;

        if (!isValidCursorPosition) {
            return input;
        }

        boolean isUserInputsDecimalSeparator = currency.getDecimalSeparator().equals("" + input.charAt(currentCursorPosition - 1));
        if (isUserInputsDecimalSeparator) {
            input = input.substring(0, currentCursorPosition) + input.substring(currentCursorPosition + 1);
        }

        return input;
    }

    private int getSpecialCharactersCountOfStringTillPosition(String aString, int aPosition) {
        String substring = aPosition < aString.length() ? aString.substring(0, aPosition) : aString;
        return substring.replaceAll("[0-9]", "").length();
    }

    private String getDecimalNumber(String input, int positionOfDecimalSeparator) {
        return positionOfDecimalSeparator > 0 ? input.substring(positionOfDecimalSeparator) : currency.getDecimalSeparator() + "00";
    }

    private String getWholeNumber(String input, int positionOfDecimalSeparator) {
        String wholeNumber;
        if (positionOfDecimalSeparator > 0) {
            wholeNumber = input.substring(0, positionOfDecimalSeparator);
        } else {
            wholeNumber = input;
        }
        return wholeNumber;
    }

    @Override
    public char getMaskCharacter() {
        return '#';
    }

    @Override
    public String getTextWhenEmpty() {
        return "";
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
