package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class FormatTextWatcher implements TextWatcher {
    private EditText editText;

    public FormatTextWatcher(EditText editText) {
        this.editText = editText;
    }

    public void init() {
        editText.setText("");
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
