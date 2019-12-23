package com.androidwidgets.formatedittext.presenter;

import android.text.InputFilter;

import com.androidwidgets.formatedittext.view.FormatEditTextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FormatEditTextPresenter {
    private boolean isOnSelectionChangeEnable;
    private FormatEditTextView view;

    public FormatEditTextPresenter(FormatEditTextView view) {
        this.view = view;
    }

    public InputFilter[] addInputFilterTo(InputFilter[] existingFilters, InputFilter newFilter) {
        Set<InputFilter> filters = new HashSet<>(Arrays.asList(existingFilters));
        filters.add(newFilter);
        return filters.toArray(new InputFilter[filters.size()]);
    }

    public InputFilter[] removeInputFilterTo(InputFilter[] existingFilters, InputFilter newFilter) {
        Set<InputFilter> filters = new HashSet<>(Arrays.asList(existingFilters));
        filters.remove(newFilter);
        return filters.toArray(new InputFilter[filters.size()]);
    }

    public void getStartSelection(int cursorPosition, String format, String input) {
        int firstPossibleIndex = format.indexOf('-');
        int lastPossibleCursorPosition = format.lastIndexOf('-') + 1;

        if (input == null || input.isEmpty()) {
            view.setCursorPosition(0);
            return;
        }

        if (cursorPosition < firstPossibleIndex) {
            cursorPosition = firstPossibleIndex;
        }

        if (cursorPosition > lastPossibleCursorPosition) {
            cursorPosition = lastPossibleCursorPosition;
        }

        view.setCursorPosition(cursorPosition);
    }

    public void setIsOnSelectionChangeEnable(boolean isOnSelectionChangeEnable) {
        this.isOnSelectionChangeEnable = isOnSelectionChangeEnable;
    }

    public boolean isOnSelectionChangeEnable() {
        return isOnSelectionChangeEnable;
    }
}
