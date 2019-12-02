package com.widgets.edittextformatter.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.widgets.edittextformatter.utils.FormatTextWatcher;

public class FormatEditText extends AppCompatEditText {


    private FormatTextWatcher.Formatter formatter;
    private boolean isOnSelectionChangeEnable;

    public FormatEditText(Context context) {
        super(context);
    }

    public FormatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {

        if (selStart == selEnd && formatter != null && isOnSelectionChangeEnable) {
            selStart = getStartSelection(selStart, formatter.getFormat(), getText().toString());
            selStart = getLastSelection(selStart, formatter.getFormat(), getText().toString());

            selEnd = selStart;

            updateWhenCursorIsInInvalidPosition(selStart, selEnd);
        }
    }

    private void updateWhenCursorIsInInvalidPosition(int selStart, int selEnd) {
        boolean isCursorIsInInvalidPosition = selStart != -1;
        if (isCursorIsInInvalidPosition) {
            setSelection(selStart, selEnd);
        }
    }

    public static int getStartSelection(int startSelection, String format, String input) {
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

    public static int getLastSelection(int startSelection, String format, String input) {
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

    public void initWith(FormatTextWatcher.Formatter formatter) {
        this.formatter = formatter;
        enableOnSelectionChange();
    }

    public void disableOnSelectionChange() {
        isOnSelectionChangeEnable = false;
    }

    public void enableOnSelectionChange() {
        isOnSelectionChangeEnable = true;
    }
}
