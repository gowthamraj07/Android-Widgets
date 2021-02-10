package com.androidwidget.formatedittext.presenter;

import android.text.InputFilter;

import com.androidwidget.formatedittext.view.FormatEditTextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FormatEditTextPresenter {
    private boolean isOnSelectionChangeEnable;
    private final FormatEditTextView view;

    public FormatEditTextPresenter(FormatEditTextView view) {
        this.view = view;
    }

    public void addInputFilterTo(InputFilter[] existingFilters, InputFilter newFilter) {
        Set<InputFilter> filters = new HashSet<>(Arrays.asList(existingFilters));
        filters.add(newFilter);
        InputFilter[] newFilters = filters.toArray(new InputFilter[filters.size()]);
        view.setFilters(newFilters);
    }

    public void removeInputFilterTo(InputFilter[] existingFilters, InputFilter newFilter) {
        Set<InputFilter> filters = new HashSet<>(Arrays.asList(existingFilters));
        filters.remove(newFilter);
        InputFilter[] newFilters = filters.toArray(new InputFilter[filters.size()]);
        view.setFilters(newFilters);
    }

    public void setCursorPosition(int cursorPosition, String format, String input, char maskCharacter) {
        int firstPossibleCursorPosition = format.indexOf(maskCharacter);
        int lastPossibleCursorPosition = format.lastIndexOf(maskCharacter) + 1;

        if (input == null || input.isEmpty()) {
            view.setCursorPosition(0);
            return;
        }

        if (isValidPosition(cursorPosition, firstPossibleCursorPosition, lastPossibleCursorPosition)) {
            view.setCursorPosition(-1);
            return;
        }

        int possiblePosition = possiblePosition(cursorPosition, firstPossibleCursorPosition, lastPossibleCursorPosition);
        view.setCursorPosition(possibleCursorPositionFor(input, possiblePosition));
    }

    private int possibleCursorPositionFor(String input, int possiblePosition) {
        return possiblePosition > input.length() ? input.length() : possiblePosition;
    }

    private int possiblePosition(int cursorPosition, int firstPossibleIndex, int lastPossibleCursorPosition) {
        return cursorPosition < firstPossibleIndex ? firstPossibleIndex : lastPossibleCursorPosition;
    }

    private boolean isValidPosition(int cursorPosition, int firstPossibleIndex, int lastPossibleCursorPosition) {
        return cursorPosition >= firstPossibleIndex && cursorPosition <= lastPossibleCursorPosition;
    }

    public void setIsOnSelectionChangeEnable(boolean isOnSelectionChangeEnable) {
        this.isOnSelectionChangeEnable = isOnSelectionChangeEnable;
    }

    public boolean isOnSelectionChangeEnable() {
        return isOnSelectionChangeEnable;
    }

    public void onTextFieldHas(boolean focus) {
        if (focus) {
            view.addWatcherOnFocus();
        } else {
            view.removeWatcherOnLostFocus();
        }
    }
}
