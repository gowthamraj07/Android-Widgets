package com.androidwidgets.formatedittext.view;

import android.text.InputFilter;

public interface FormatEditTextView {
    void setCursorPosition(int cursorPosition);
    void setFilters(InputFilter[] filters);

    void addWatcherOnFocus();
    void removeWatcherOnLostFocus();
}
