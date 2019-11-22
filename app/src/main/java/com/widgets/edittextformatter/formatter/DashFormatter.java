package com.widgets.edittextformatter.formatter;

import com.widgets.edittextformatter.utils.FormatTextWatcher;
import com.widgets.edittextformatter.utils.Result;

public class DashFormatter implements FormatTextWatcher.Formatter {
    private final String format;

    public DashFormatter(String format) {
        this.format = format;
    }

    @Override
    public Result format(final String input, final int currentCursorPosition) {

        String unformattedInput = removeFormatFrom(input);

        String resultString = format;
        for (char ch : unformattedInput.toCharArray()) {
            resultString = resultString.replaceFirst("\\-", "" + ch);
        }

        resultString = resultString.replaceAll("\\-", " ");
        resultString = resultString.trim();

        int noOfNonDashCharactersInInput = getNonDashCharactersCountFrom(input, currentCursorPosition);
        String truncatedFormatForInputCharacter = format.substring(0, currentCursorPosition);
        String formatWithoutDashes = truncatedFormatForInputCharacter.replaceAll("-", "");
        int noOfNonDashCharacters = formatWithoutDashes.length();

        int formattedCursorPosition = currentCursorPosition + noOfNonDashCharacters - noOfNonDashCharactersInInput + format.indexOf("-");
        if(formattedCursorPosition > resultString.length()) {
            return new Result(resultString, resultString.length());
        }

        return new Result(resultString, formattedCursorPosition);
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

    private int getNonDashCharactersCountFrom(String input, int tillCurrentCursorPosition) {
        int nonDashCharacterCount = 0;
        for (int index = 0; index < input.length() && index < format.length() && index < tillCurrentCursorPosition; index++) {
            if (format.charAt(index) != '-') {
                if(format.charAt(index) == input.charAt(index)) {
                    nonDashCharacterCount++;
                }
            }
        }
        return nonDashCharacterCount;
    }
}
