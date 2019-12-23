package com.androidwidgets.formatedittext.widgets;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.androidwidgets.formatedittext.formatter.DashFormatter;
import com.androidwidgets.formatedittext.presenter.FormatEditTextPresenter;
import com.androidwidgets.formatedittext.utils.FormatTextWatcher;
import com.androidwidgets.formatedittext.view.FormatEditTextView;

public class FormatEditText extends AppCompatEditText implements FormatEditTextView {

    private FormatEditTextPresenter formatEditTextPresenter;
    private FormatTextWatcher.Formatter formatter;
    private FormatTextWatcher.Validator validator;
    private FormatTextWatcher.ValidationListener validationListener;

    public FormatEditText(Context context) {
        super(context);
        init();
    }

    public FormatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FormatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        formatEditTextPresenter = new FormatEditTextPresenter(this);
    }

    public void disableOnSelectionChange() {
        formatEditTextPresenter.setIsOnSelectionChangeEnable(false);
    }

    public void enableOnSelectionChange() {
        formatEditTextPresenter.setIsOnSelectionChangeEnable(true);
    }

    public void setFormat(final String format) {
        this.setOnFocusChangeListener(new OnFocusChangeListener(format));
    }

    @SuppressWarnings("unused")
    public void addFilter(InputFilter.LengthFilter inputFilter) {
        formatEditTextPresenter.addInputFilterTo(getFilters(), inputFilter);
    }

    @SuppressWarnings("unused")
    public void removeFilter(InputFilter.LengthFilter inputFilter) {
        formatEditTextPresenter.removeInputFilterTo(getFilters(), inputFilter);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (!isEditTextEditable(selStart, selEnd)) {
            return;
        }

        formatEditTextPresenter.setCursorPosition(selStart, formatter.getFormat(), getText().toString());
    }

    private boolean isEditTextEditable(int selStart, int selEnd) {
        return selStart == selEnd
                && formatter != null
                && formatEditTextPresenter.isOnSelectionChangeEnable();
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

    @Override
    public void setCursorPosition(int cursorPosition) {
        updateWhenCursorIsInInvalidPosition(cursorPosition, cursorPosition);
    }

    public void setValidator(FormatTextWatcher.Validator validator, FormatTextWatcher.ValidationListener listener) {
        this.validationListener = listener;
        this.validator = validator;
    }

    private class OnFocusChangeListener implements View.OnFocusChangeListener {
        private final FormatTextWatcher.Formatter formatter;

        OnFocusChangeListener(String format) {
            formatter = new DashFormatter(format);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            FormatTextWatcher textWatcher = new FormatTextWatcher(FormatEditText.this, formatter, validator, validationListener);
            if (hasFocus) {
                initWith(formatter);
                textWatcher.init();
                textWatcher.setInitialText();
                addTextChangedListener(textWatcher);
            } else {
                removeTextChangedListener(textWatcher);
            }
        }
    }
}
