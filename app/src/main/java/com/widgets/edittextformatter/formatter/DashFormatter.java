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
        if (unformattedInput.isEmpty()) {
            return new Result(format.replaceAll("-"," "), getFirstPossibleCursorPosition());
        }

        int cursorPositionWhenUnformattedInput = calculateCursorPositionForUnformattedInput(input.trim(), currentCursorPosition);

        String resultString = format;
        for (char ch : unformattedInput.toCharArray()) {
            resultString = resultString.replaceFirst("\\-", "" + ch);
        }

        resultString = resultString.replaceAll("\\-", " ");
        resultString = resultString.trim();

        int formattedCursorPosition = format.indexOf('-');
        for (int index = 0; index < unformattedInput.length() && index < cursorPositionWhenUnformattedInput; index++) {
            formattedCursorPosition = format.indexOf("-", formattedCursorPosition) + 1;
        }

        if (formattedCursorPosition > resultString.length()) {
            return new Result(resultString, resultString.length());
        }

        return new Result(resultString, formattedCursorPosition);
    }

    private int getFirstPossibleCursorPosition() {
        return format.indexOf('-');
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public boolean canAcceptMoreCharacters(String previousText) {
        return removeFormatFrom(previousText).length() < getPossibleInputCount();
    }

    @Override
    public String removeFormat(String userInput) {
        return removeFormatFrom(userInput);
    }

    private int getPossibleInputCount() {
        int charCount = 0;
        for (int index = 0; index < format.length(); index++) {
            if (format.charAt(index) == '-') {
                charCount++;
            }
        }

        return charCount;
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
        String inputCopy = input;
        String formatCopy = format;
        for (int index = 0; index < formatCopy.length(); index++) {
            inputCopy = inputCopy.replaceAll("\\"+formatCopy.charAt(index), "");
        }

        return inputCopy.trim();
    }

}
