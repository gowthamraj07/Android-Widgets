package com.androidwidgets.formatedittext.utils;

import android.view.View;

import com.androidwidgets.formatedittext.widgets.FormatEditText;

public class FormatEditTextFactory {
    private String format;
    private FormatTextWatcher.Validator validator;
    private FormatTextWatcher.ValidationListener validationListener;
    private final FormatEditText formatEditText;

    public FormatEditTextFactory(View rootView, int formatEditTextId) {
        formatEditText = rootView.findViewById(formatEditTextId);
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
        formatEditText.setValidator(validator);
        formatEditText.setValidationListener(validationListener);
        formatEditText.setFormat(format);
        return formatEditText;
    }
}
