package com.androidwidgets.formatedittext.formatter;

import com.androidwidgets.formatedittext.utils.FormatTextWatcher;
import com.androidwidgets.formatedittext.utils.Result;

public class DashFormatter implements FormatTextWatcher.Formatter {
    private final String format;

    public DashFormatter(String format) {
        this.format = format;
    }

    @Override
    public Result format(final String input, final int currentCursorPosition) {

        String unformattedInput = removeFormatFrom(input);
        if (unformattedInput.isEmpty()) {
            return new Result(format.replaceAll("-", " "), getFirstPossibleCursorPosition());
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

        return new Result(resultString, formattedCursorPosition);
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public String removeFormat(String userInput) {
        return removeFormatFrom(userInput);
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

    private int getFirstPossibleCursorPosition() {
        return format.indexOf('-');
    }

    String removeFormatFrom(final String input) {
        String inputCopy = input;
        String formatCopy = format;
        for (int index = 0; index < formatCopy.length(); index++) {
            char characterAtIndex = formatCopy.charAt(index);
            inputCopy = removeCharacterFromInput(inputCopy, index, characterAtIndex);
        }

        return inputCopy.replaceAll(" ", "").trim();
    }

    private String removeCharacterFromInput(String inputCopy, int index, char characterAtIndex) {
        if (isDigit(characterAtIndex) || isAlphabet(characterAtIndex)) {
            return replaceCharaterPositionWithSpace(inputCopy, index);
        } else {
            return replaceAllMatchedCharactersWithSpace(inputCopy, characterAtIndex);
        }
    }

    private boolean isAlphabet(char characterAtIndex) {
        return (characterAtIndex >= 'a' && characterAtIndex <= 'z')
                || (characterAtIndex >= 'A' && characterAtIndex <= 'Z');
    }

    private String replaceAllMatchedCharactersWithSpace(String inputCopy, char characterAtIndex) {
        return inputCopy.replaceAll("\\" + characterAtIndex, " ");
    }

    private String replaceCharaterPositionWithSpace(String inputCopy, int index) {
        return inputCopy.substring(0, index) + " " + inputCopy.substring(index + 1);
    }

    private boolean isDigit(char characterAtIndex) {
        return characterAtIndex > 47 && characterAtIndex < 58;
    }

}
