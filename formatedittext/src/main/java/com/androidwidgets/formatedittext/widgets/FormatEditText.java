package com.androidwidgets.formatedittext.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.androidwidgets.formatedittext.formatter.DashFormatter;
import com.androidwidgets.formatedittext.utils.FormatTextWatcher;

public class FormatEditText extends AppCompatEditText {

    private boolean isOnSelectionChangeEnable;
    private FormatTextWatcher.Formatter formatter;
    private FormatTextWatcher.Validator validator;
    private FormatTextWatcher.ValidationListener validationListener;

    public FormatEditText(Context context) {
        super(context);
    }

    public FormatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void disableOnSelectionChange() {
        isOnSelectionChangeEnable = false;
    }

    public void enableOnSelectionChange() {
        isOnSelectionChangeEnable = true;
    }

    public void setFormat(String format) {
        FormatTextWatcher.Formatter formatter = new DashFormatter(format);
        final FormatTextWatcher textWatcher = new FormatTextWatcher(this, formatter, validator, validationListener);
        this.initWith(formatter);
        textWatcher.init();

        this.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textWatcher.setInitialText();
                    FormatEditText.this.addTextChangedListener(textWatcher);
                } else {
                    FormatEditText.this.removeTextChangedListener(textWatcher);
                }
            }
        });
    }

    public void setValidator(FormatTextWatcher.Validator validator) {
        this.validator = validator;
    }

    public void setValidationListener(FormatTextWatcher.ValidationListener validationListener) {
        this.validationListener = validationListener;
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

    static int getStartSelection(int startSelection, String format, String input) {
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

    static int getLastSelection(int startSelection, String format, String input) {
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

    private void updateWhenCursorIsInInvalidPosition(int selStart, int selEnd) {
        boolean isCursorIsInInvalidPosition = selStart != -1;
        if (isCursorIsInInvalidPosition) {
            setSelection(selStart, selEnd);
        }
    }

    private void initWith(FormatTextWatcher.Formatter formatter) {
        this.formatter = formatter;
        enableOnSelectionChange();
    }
}