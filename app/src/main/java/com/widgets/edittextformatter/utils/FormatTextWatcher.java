package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class FormatTextWatcher implements TextWatcher {
    private String format;

    public FormatTextWatcher(String format) {
        this.format = format;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
