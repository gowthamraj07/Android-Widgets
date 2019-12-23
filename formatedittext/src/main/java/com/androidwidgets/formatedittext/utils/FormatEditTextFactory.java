package com.androidwidgets.formatedittext.utils;

import android.view.View;

import com.androidwidgets.formatedittext.widgets.FormatEditText;

public class FormatEditTextFactory {
    private String format = "";
    private FormatTextWatcher.Validator validator = new EmptyValidator();
    private FormatTextWatcher.ValidationListener validationListener = new EmptyValidationListener();
    private final FormatEditText formatEditText;

    private FormatEditTextFactory(View rootView, int formatEditTextId) {
        formatEditText = rootView.findViewById(formatEditTextId);
    }

    public static FormatEditTextFactory create(View rootView, int formatEditTextId) {
        return new FormatEditTextFactory(rootView, formatEditTextId);
    }

    public FormatEditTextFactory setFormat(String format) {
        this.format = format;
        return this;
    }

    public FormatEditTextFactory setValidation(FormatTextWatcher.Validator validator, FormatTextWatcher.ValidationListener validationListener) {
        this.validator = validator;
        this.validationListener = validationListener;
        return this;
    }

    public FormatEditText build() {
        formatEditText.setValidator(validator, validationListener);
        formatEditText.setFormat(format);
        return formatEditText;
    }

    class EmptyValidator implements FormatTextWatcher.Validator {
        @Override
        public boolean validate(String formattedUserInput, String unformattedUserInput) {
            return true;
        }
    }

    public class EmptyValidationListener implements FormatTextWatcher.ValidationListener {
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
