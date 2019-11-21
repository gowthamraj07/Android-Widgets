package com.widgets.edittextformatter.utils;

class MyResult {
    private final String formattedUserInput;
    private final int formattedCursorPosition;

    public MyResult(String formattedUserInput, int formattedCursorPosition) {

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
