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
            return emptyFormatWithCursorInFirstPossiblePosition();
        }

        String formattedInput = format(unformattedInput);
        int formattedCursorPosition = getFormattedCursorPosition(input, currentCursorPosition, unformattedInput);
        return new Result(formattedInput, formattedCursorPosition);
    }

    @Override
    public char getMaskCharacter() {
        return '-';
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public String removeFormat(String userInput) {
        return removeFormatFrom(userInput);
    }

    private int getFormattedCursorPosition(String input, int currentCursorPosition, String unformattedInput) {
        int formattedCursorPosition = format.indexOf('-');
        int cursorPositionWhenUnformattedInput = calculateCursorPositionForUnformattedInput(input.trim(), currentCursorPosition);
        for (int index = 0; index < unformattedInput.length() && index < cursorPositionWhenUnformattedInput; index++) {
            formattedCursorPosition = format.indexOf("-", formattedCursorPosition) + 1;
        }
        return formattedCursorPosition;
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

    private String format(String unformattedInput) {
        String resultString = replaceDashInFormatWith(unformattedInput);
        resultString = replaceDashInFormatWithSpace(resultString);
        resultString = resultString.trim();
        return resultString;
    }

    private String replaceDashInFormatWithSpace(String resultString) {
        return resultString.replaceAll("\\-", " ");
    }

    private String replaceDashInFormatWith(String unformattedInput) {
        String resultString = format;
        for (char ch : unformattedInput.toCharArray()) {
            resultString = resultString.replaceFirst("\\-", "" + ch);
        }
        return resultString;
    }

    private Result emptyFormatWithCursorInFirstPossiblePosition() {
        return new Result(format.replaceAll("-", " "), getFirstPossibleCursorPosition());
    }

    private int getFirstPossibleCursorPosition() {
        return format.indexOf('-');
    }

    private String removeFormatFrom(final String input) {
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
            return replaceCharacterPositionWithSpace(inputCopy, index);
        } else {
            return replaceAllMatchedCharactersWithSpace(inputCopy, characterAtIndex);
        }
    }

    private boolean isDigit(char characterAtIndex) {
        return characterAtIndex > 47 && characterAtIndex < 58;
    }

    private boolean isAlphabet(char characterAtIndex) {
        return (characterAtIndex >= 'a' && characterAtIndex <= 'z')
                || (characterAtIndex >= 'A' && characterAtIndex <= 'Z');
    }

    private String replaceAllMatchedCharactersWithSpace(String inputCopy, char characterAtIndex) {
        return inputCopy.replaceAll("\\" + characterAtIndex, " ");
    }

    private String replaceCharacterPositionWithSpace(String inputCopy, int index) {
        return inputCopy.substring(0, index) + " " + inputCopy.substring(index + 1);
    }
}
