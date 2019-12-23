package com.androidwidgets.formatedittext.widgets;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.androidwidgets.formatedittext.formatter.DashFormatter;
import com.androidwidgets.formatedittext.presenter.FormatEditTextPresenter;
import com.androidwidgets.formatedittext.utils.FormatTextWatcher;

public class FormatEditText extends AppCompatEditText {

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
        FormatEditTextPresenter.isOnSelectionChangeEnable = false;
    }

    public void enableOnSelectionChange() {
        FormatEditTextPresenter.isOnSelectionChangeEnable = true;
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

    @SuppressWarnings("unused")
    public void addFilter(InputFilter.LengthFilter inputFilter) {
        setFilters(FormatEditTextPresenter.addInputFilterTo(getFilters(), inputFilter));
    }

    @SuppressWarnings("unused")
    public void removeFilter(InputFilter.LengthFilter inputFilter) {
        setFilters(FormatEditTextPresenter.removeInputFilterTo(getFilters(), inputFilter));
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (isEditTextEditable(selStart, selEnd)) {
            selStart = FormatEditTextPresenter.getStartSelection(selStart, formatter.getFormat(), getText().toString());
            selStart = FormatEditTextPresenter.getLastSelection(selStart, formatter.getFormat(), getText().toString());

            selEnd = selStart;

            updateWhenCursorIsInInvalidPosition(selStart, selEnd);
        }
    }

    private boolean isEditTextEditable(int selStart, int selEnd) {
        return selStart == selEnd && formatter != null && FormatEditTextPresenter.isOnSelectionChangeEnable;
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
