package com.widgets.edittextformatter.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.widgets.edittextformatter.utils.FormatTextWatcher;

public class FormatEditText extends AppCompatEditText {


    private FormatTextWatcher.Formatter formatter;

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

        if (selStart == selEnd && formatter != null) {
            selStart = getStartSelection(selStart, formatter.getFormat(), getText().toString());
            selStart = getLastSelection(selStart, formatter.getFormat(), getText().toString());

            selEnd = selStart;

            setSelection(selStart, selEnd);
        }
    }

    public static int getStartSelection(int startSelection, String format, String input) {
        int firstPossibleIndex = format.indexOf('-');

        if (input == null || input.isEmpty()) {
            return 0;
        }

        return startSelection < firstPossibleIndex ? firstPossibleIndex : startSelection;
    }

    public static int getLastSelection(int startSelection, String format, String input) {
        int lastPossibleCursorPosition = format.lastIndexOf('-') + 1;

        if (input == null || input.isEmpty()) {
            return 0;
        }

        return startSelection > lastPossibleCursorPosition ? lastPossibleCursorPosition : startSelection;
    }

    public void initWith(FormatTextWatcher.Formatter formatter) {
        this.formatter = formatter;
    }
}
