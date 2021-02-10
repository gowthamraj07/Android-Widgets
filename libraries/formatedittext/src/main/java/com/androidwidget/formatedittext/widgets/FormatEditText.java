package com.androidwidget.formatedittext.widgets;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.androidwidget.formatedittext.formatter.DashFormatter;
import com.androidwidget.formatedittext.presenter.FormatEditTextPresenter;
import com.androidwidget.formatedittext.utils.FormatTextWatcher;
import com.androidwidget.formatedittext.view.FormatEditTextView;

public class FormatEditText extends AppCompatEditText implements FormatEditTextView {

    private FormatEditTextPresenter formatEditTextPresenter;
    private FormatTextWatcher.Formatter formatter;
    private FormatTextWatcher.Validator validator = new AValidator();
    private FormatTextWatcher.ValidationListener validationListener = new AListener();
    private FormatTextWatcher textWatcher;
    private String format;

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

    public void setFormat(final String format) {
        this.format = format;
        setFormatter(new DashFormatter(format));
    }

    public void setFormatter(FormatTextWatcher.Formatter formatter) {
        this.formatter = formatter;
        this.setOnFocusChangeListener(new OnFocusChangeListener());
    }

    public void setValidator(FormatTextWatcher.Validator validator, FormatTextWatcher.ValidationListener listener) {
        this.validationListener = listener;
        this.validator = validator;
    }

    @SuppressWarnings("unused")
    public void addFilter(InputFilter.LengthFilter inputFilter) {
        formatEditTextPresenter.addInputFilterTo(getFilters(), inputFilter);
    }

    @SuppressWarnings("unused")
    public void removeFilter(InputFilter.LengthFilter inputFilter) {
        formatEditTextPresenter.removeInputFilterTo(getFilters(), inputFilter);
    }

    public void disableOnSelectionChange() {
        formatEditTextPresenter.setIsOnSelectionChangeEnable(false);
    }

    public void enableOnSelectionChange() {
        formatEditTextPresenter.setIsOnSelectionChangeEnable(true);
    }

    @Override
    public void setCursorPosition(int cursorPosition) {
        boolean isCursorIsInInvalidPosition = cursorPosition != -1;
        if (isCursorIsInInvalidPosition) {
            setSelection(cursorPosition, cursorPosition);
        }
    }

    @Override
    public void addWatcherOnFocus() {
        textWatcher = new FormatTextWatcher(FormatEditText.this, formatter, validator, validationListener);
        enableOnSelectionChange();
        textWatcher.init();
        textWatcher.setInitialTextWhenEmpty();
        addTextChangedListener(textWatcher);
    }

    @Override
    public void removeWatcherOnLostFocus() {
        removeTextChangedListener(textWatcher);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (!isEditTextEditable(selStart, selEnd)) {
            return;
        }

        formatEditTextPresenter.setCursorPosition(selStart, formatter.getFormat(), getText().toString(), formatter.getMaskCharacter());
    }

    private void init() {
        formatEditTextPresenter = new FormatEditTextPresenter(this);
    }

    private boolean isEditTextEditable(int selStart, int selEnd) {
        return selStart == selEnd
                && formatter != null
                && formatEditTextPresenter.isOnSelectionChangeEnable();
    }

    private class OnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean focus) {
            formatEditTextPresenter.onTextFieldHas(focus);
        }
    }

    private class AValidator implements FormatTextWatcher.Validator {
        @Override
        public boolean validate(String formattedUserInput, String unformattedUserInput) {
            return true;
        }
    }

    private class AListener implements FormatTextWatcher.ValidationListener {
        @Override
        public void showSuccess() {

        }

        @Override
        public void showError() {

        }

        @Override
        public void showEmpty() {

        }
    }
}
