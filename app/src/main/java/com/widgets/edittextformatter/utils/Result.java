package com.widgets.edittextformatter.utils;

public class Result {
    private final String formattedUserInput;
    private final int formattedCursorPosition;

    public Result(String formattedUserInput, int formattedCursorPosition) {

        this.formattedUserInput = formattedUserInput;
        this.formattedCursorPosition = formattedCursorPosition;
    }

    public String getFormattedUserInput() {
        return formattedUserInput;
    }

    public int getFormattedCursorPosition() {
        return formattedCursorPosition;
    }
}
