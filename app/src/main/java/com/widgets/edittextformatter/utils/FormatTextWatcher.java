package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class FormatTextWatcher implements TextWatcher {

    private static final String TAG = FormatTextWatcher.class.getSimpleName();

    private EditText editText;

    public FormatTextWatcher(EditText editText) {
        this.editText = editText;
    }

    void init() {
        editText.setText("");
        editText.setSelection(0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "beforeTextChanged: s=" + s + ", start=" + start + ", count=" + count + ", after=" + after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChanged: s=" + s + ", start=" + start + ", before=" + before + ", count=" + count);
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged: s=" + s);
    }
}
