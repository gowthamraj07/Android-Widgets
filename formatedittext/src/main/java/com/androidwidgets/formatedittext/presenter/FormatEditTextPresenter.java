package com.androidwidgets.formatedittext.presenter;

import android.text.InputFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FormatEditTextPresenter {
    private boolean isOnSelectionChangeEnable;

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

    public int getLastSelection(int startSelection, String format, String input) {
        int firstPossibleIndex = format.indexOf('-');
        int lastPossibleCursorPosition = format.lastIndexOf('-') + 1;

        if (input == null || input.isEmpty()) {
            return 0;
        }

        int result = startSelection > lastPossibleCursorPosition ? lastPossibleCursorPosition : startSelection;
        if (result > firstPossibleIndex && result < lastPossibleCursorPosition) {
            return -1;
        }

        return result;
    }

    public int getStartSelection(int startSelection, String format, String input) {
        int firstPossibleIndex = format.indexOf('-');
        int lastPossibleCursorPosition = format.lastIndexOf('-') + 1;

        if (input == null || input.isEmpty()) {
            return 0;
        }

        int result = startSelection < firstPossibleIndex ? firstPossibleIndex : startSelection;
        if (result > firstPossibleIndex && result < lastPossibleCursorPosition) {
            return -1;
        }

        return result;
    }

    public void setIsOnSelectionChangeEnable(boolean isOnSelectionChangeEnable) {
        this.isOnSelectionChangeEnable = isOnSelectionChangeEnable;
    }

    public boolean isOnSelectionChangeEnable() {
        return isOnSelectionChangeEnable;
    }
}
