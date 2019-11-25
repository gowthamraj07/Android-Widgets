package com.widgets.edittextformatter.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public class FormatTextWatcher implements TextWatcher {

    private static final String TAG = FormatTextWatcher.class.getSimpleName();

    private EditText editText;
    private Formatter formatter;
    private boolean editable = true;
    private String previousText;
    private boolean isDelete = false;

    public FormatTextWatcher(EditText editText, Formatter formatter) {
        this.editText = editText;
        this.formatter = formatter;
    }

    public void init() {
        String format = formatter.getFormat();
        editText.setHint(format);
        editText.setSelection(0);
        editText.setText("");

        int maxLengthOfEditText = format.length() + 1;
        setEditTextMaxLength(maxLengthOfEditText);
    }

    protected void setEditTextMaxLength(int maxLengthOfEditText) {
        InputFilter filter = new InputFilter.LengthFilter(maxLengthOfEditText);
        editText.setFilters(new InputFilter[]{filter});
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Log.d(TAG, "beforeTextChanged: s=" + s + ", start=" + start + ", count=" + count + ", after=" + after);
        previousText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Log.d(TAG, "onTextChanged: s=" + s + ", start=" + start + ", before=" + before + ", count=" + count);
        isDelete = before != 0;
    }

    @Override
    public void afterTextChanged(Editable s) {
        //Log.d(TAG, "afterTextChanged: s=" + s);

        if (!editable) return;

        editable = false;

        int formatLength = formatter.getFormat().length();
        if (!formatter.canAcceptMoreCharacters(previousText) && !isDelete) {
            maintainSameText(formatLength, editText, previousText);
        } else {
            Result formattedInput = formatter.format(s.toString(), editText.getSelectionStart());
            editText.setText(formattedInput.getFormattedUserInput());
            editText.setSelection(formattedInput.getFormattedCursorPosition());
        }

        editable = true;
    }

    public interface Formatter {
        Result format(String input, int currentCursorPosition);
        String getFormat();
        boolean canAcceptMoreCharacters(String previousText);
    }

    private boolean isAlreadyReachedMaximumLength(Editable s, int formatLength) {
        return s.length() > formatLength;
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
}
