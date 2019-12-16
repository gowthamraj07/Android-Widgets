package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;

import com.widgets.edittextformatter.widgets.FormatEditText;

public class FormatTextWatcher implements TextWatcher {

    private FormatEditText editText;
    private Formatter formatter;
    private final Validator validator;
    private final ValidationListener listener;
    private boolean editable = true;

    public FormatTextWatcher(FormatEditText editText, Formatter formatter) {
        this.editText = editText;
        this.formatter = formatter;
        this.validator = new EmptyValidator();
        this.listener = new EmptyListener();
    }

    public FormatTextWatcher(FormatEditText editText, Formatter formatter, Validator validator, ValidationListener listener) {
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

        listener.showEmpty();

        int maxLengthOfEditText = format.length() + 1;
        setEditTextMaxLength(maxLengthOfEditText);
    }

    protected void setEditTextMaxLength(int maxLengthOfEditText) {
        InputFilter filter = new InputFilter.LengthFilter(maxLengthOfEditText);
        editText.setFilters(new InputFilter[]{filter});
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!editable) {
            return;
        }

        editable = false;

        String userInput = s.toString();
        Result formattedInput = formatter.format(userInput, editText.getSelectionStart());

        editText.disableOnSelectionChange();
        editText.setText(formattedInput.getFormattedUserInput());
        editText.setSelection(formattedInput.getFormattedCursorPosition());
        editText.enableOnSelectionChange();

        if (validator.validate(formattedInput.getFormattedUserInput(), formatter.removeFormat(userInput))) {
            listener.showSuccess();
        } else {
            listener.showError();
        }


        editable = true;
    }

    public void setInitialText() {
        String format = formatter.getFormat();
        editText.setText(format.replaceAll("-", " "));
    }

    public void setInitialTextWhenEmpty() {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            setInitialText();
        } else {
            editText.setText(text);
        }
    }

    public interface Formatter {
        Result format(String input, int currentCursorPosition);

        String getFormat();

        boolean canAcceptMoreCharacters(String previousText);

        String removeFormat(String userInput);
    }

    public interface Validator {
        boolean validate(String formattedUserInput, String unformattedUserInput);
    }

    public interface ValidationListener {
        void showSuccess();

        void showError();

        void showEmpty();
    }

    private class EmptyValidator implements Validator {
        @Override
        public boolean validate(String formattedUserInput, String unformattedUserInput) {
            return true;
        }
    }

    private class EmptyListener implements ValidationListener {
        @Override
        public void showSuccess() {

        }

        @Override
        public void showError() {

        }

        @Override
        public void showEmpty() {

        }
    }
}
