package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public class FormatTextWatcher implements TextWatcher {

    private EditText editText;
    private Formatter formatter;
    private final Validator validator;
    private final ValidationListener listener;
    private boolean editable = true;
    private String previousText;
    private boolean isDelete = false;

    public FormatTextWatcher(EditText editText, Formatter formatter) {
        this.editText = editText;
        this.formatter = formatter;
        this.validator = new EmptyValidator();
        this.listener = new EmptyListener();
    }

    public FormatTextWatcher(EditText editText, Formatter formatter, Validator validator, ValidationListener listener) {
        this.editText = editText;
        this.formatter = formatter;
        this.validator = validator;
        this.listener = listener;
    }

    public void init() {
        String format = formatter.getFormat();
        editText.setHint(format);
        editText.setSelection(0);
        editText.setText("");

        listener.showHint();

        int maxLengthOfEditText = format.length() + 1;
        setEditTextMaxLength(maxLengthOfEditText);
    }

    protected void setEditTextMaxLength(int maxLengthOfEditText) {
        InputFilter filter = new InputFilter.LengthFilter(maxLengthOfEditText);
        editText.setFilters(new InputFilter[]{filter});
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        previousText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isDelete = before != 0;
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!editable) return;

        editable = false;

        int formatLength = formatter.getFormat().length();
        if (!formatter.canAcceptMoreCharacters(previousText) && !isDelete) {
            maintainSameText(formatLength, editText, previousText);
        } else {
            String userInput = s.toString();
            Result formattedInput = formatter.format(userInput, editText.getSelectionStart());
            editText.setText(formattedInput.getFormattedUserInput());
            editText.setSelection(formattedInput.getFormattedCursorPosition());

            if (validator.validate(formattedInput.getFormattedUserInput(), formatter.removeFormat(userInput))) {
                listener.showHint();
            } else {
                listener.showError();
            }
        }

        editable = true;
    }

    public interface Formatter {
        Result format(String input, int currentCursorPosition);
        String getFormat();
        boolean canAcceptMoreCharacters(String previousText);
        String removeFormat(String userInput);
    }

    private void maintainSameText(int formatLength, EditText editText, String previousText) {
        int cursorPosition = editText.getSelectionStart() - 1;
        editText.setText(previousText);

        if (cursorPosition > formatLength) {
            editText.setSelection(formatLength);
        } else {
            editText.setSelection(cursorPosition);
        }
    }

    public interface Validator {
        boolean validate(String formattedUserInput, String unformattedUserInput);
    }

    public interface ValidationListener {
        void showHint();
        void showError();
    }

    private class EmptyValidator implements Validator {
        @Override
        public boolean validate(String formattedUserInput, String unformattedUserInput) {
            return true;
        }
    }

    private class EmptyListener implements ValidationListener {
        @Override
        public void showHint() {

        }

        @Override
        public void showError() {

        }
    }
}
