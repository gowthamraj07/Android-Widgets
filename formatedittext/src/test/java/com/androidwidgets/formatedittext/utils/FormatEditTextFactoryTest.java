package com.androidwidgets.formatedittext.utils;

import android.view.View;

import com.androidwidgets.formatedittext.widgets.FormatEditText;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FormatEditTextFactoryTest {

    private static final int ANY_FORMAT_EDIT_TEXT_ID = 1234;
    private static final String ANY_FORMAT = "any format";

    @Test
    public void shouldFindTheFormatEditTextAndAssignTheValuesInOrder() {
        int formatEditTextId = ANY_FORMAT_EDIT_TEXT_ID;
        String format = ANY_FORMAT;
        View rootView = mock(View.class);
        FormatEditText formatEditText = mock(FormatEditText.class);
        when(rootView.findViewById(formatEditTextId)).thenReturn(formatEditText);
        FormatTextWatcher.Validator validator = mock(FormatTextWatcher.Validator.class);
        FormatTextWatcher.ValidationListener validationListener = mock(FormatTextWatcher.ValidationListener.class);

        FormatEditText editText = FormatEditTextFactory.create(rootView, formatEditTextId)
                .setFormat(format)
                .setValidation(validator, validationListener)
                .build();

        InOrder inOrder = Mockito.inOrder(formatEditText);
        inOrder.verify(formatEditText).setValidator(validator);
        inOrder.verify(formatEditText).setValidationListener(validationListener);
        inOrder.verify(formatEditText).setFormat(format);
        assertEquals(formatEditText, editText);
    }
}