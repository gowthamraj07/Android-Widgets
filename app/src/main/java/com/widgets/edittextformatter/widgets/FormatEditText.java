package com.widgets.edittextformatter.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class FormatEditText extends AppCompatEditText {


    public FormatEditText(Context context) {
        super(context);
    }

    public FormatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static int getStartSelection(int startSelection, String format) {
        int firstPossibleIndex = format.indexOf('-');
        return startSelection < firstPossibleIndex ? firstPossibleIndex : startSelection;
    }

    public static int getLastSelection(int startSelection, String format) {
        return format.lastIndexOf('-') + 1;
    }
}
