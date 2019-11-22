package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class FormatTextWatcher implements TextWatcher {

    private static final String TAG = FormatTextWatcher.class.getSimpleName();

    private EditText editText;
    private Formatter formatter;
    private boolean editable = true;

    public FormatTextWatcher(EditText editText, Formatter formatter) {
        this.editText = editText;
        this.formatter = formatter;
    }

    void init() {
        editText.setText("");
        editText.setSelection(0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Log.d(TAG, "beforeTextChanged: s=" + s + ", start=" + start + ", count=" + count + ", after=" + after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Log.d(TAG, "onTextChanged: s=" + s + ", start=" + start + ", before=" + before + ", count=" + count);
    }

    @Override
    public void afterTextChanged(Editable s) {
        //Log.d(TAG, "afterTextChanged: s=" + s);

        if (!editable) return;

        editable = false;

        Result formattedInput = formatter.format(s.toString(), editText.getSelectionStart());
        editText.setText(formattedInput.getFormattedUserInput());
        editText.setSelection(formattedInput.getFormattedCursorPosition());

        editable = true;
    }

    public interface Formatter {
        Result format(String input, int currentCursorPosition);
    }
}
