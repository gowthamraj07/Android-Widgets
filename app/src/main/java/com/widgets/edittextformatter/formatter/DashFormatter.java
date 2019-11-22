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
        int cursorPositionWhenUnformattedInput = calculateCursorPositionForUnformattedInput(input.trim(), currentCursorPosition);

        String resultString = format;
        for (char ch : unformattedInput.toCharArray()) {
            resultString = resultString.replaceFirst("\\-", "" + ch);
        }

        resultString = resultString.replaceAll("\\-", " ");
        resultString = resultString.trim();

        int formattedCursorPosition = 0;
        for (int index = 0; index < unformattedInput.length() && index < cursorPositionWhenUnformattedInput; index++) {
            formattedCursorPosition = format.indexOf("-", formattedCursorPosition) + 1;
        }

        if (formattedCursorPosition > resultString.length()) {
            return new Result(resultString, resultString.length());
        }

        return new Result(resultString, formattedCursorPosition);
    }

    @Override
    public String getFormat() {
        return format;
    }

    private int calculateCursorPositionForUnformattedInput(final String input, final int currentCursorPosition) {
        int resultCursorPosition = currentCursorPosition;
        for (int index = 0; index < format.length() && index < input.length() && index < currentCursorPosition; index++) {
            char charInFormatAtIndex = format.charAt(index);
            if (charInFormatAtIndex != '-' && charInFormatAtIndex == input.charAt(index)) {
                resultCursorPosition--;
            }
        }
        return resultCursorPosition;
    }

    private String removeFormatFrom(final String input) {
        String unformattedInput = "";

        String inputWithoutSpace = input.replaceAll(" ", "");
        String formatWithoutSpace = format.replaceAll(" ","");
        for (int index = 0; index < inputWithoutSpace.length() && index < formatWithoutSpace.length(); index++) {
            char charAtIndexInFormat = formatWithoutSpace.charAt(index);
            char charAtIndexInInput = inputWithoutSpace.charAt(index);

            if (charAtIndexInFormat == charAtIndexInInput) {
                continue;
            }

            if (charAtIndexInFormat == '-') {
                unformattedInput += charAtIndexInInput;
                continue;
            }

            return inputWithoutSpace;
        }

        return unformattedInput.trim();
    }

    private int getNonDashCharactersCountFrom(String input, int tillCurrentCursorPosition) {
        int nonDashCharacterCount = 0;
        for (int index = 0; index < input.length() && index < format.length() && index < tillCurrentCursorPosition; index++) {
            if (format.charAt(index) != '-') {
                if (format.charAt(index) == input.charAt(index)) {
                    nonDashCharacterCount++;
                }
            }
        }
        return nonDashCharacterCount;
    }
}
