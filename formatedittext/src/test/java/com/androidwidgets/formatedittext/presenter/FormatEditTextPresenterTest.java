package com.androidwidgets.formatedittext.presenter;

import android.text.InputFilter;

import com.androidwidgets.formatedittext.view.FormatEditTextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class FormatEditTextPresenterTest {

    private FormatEditTextPresenter presenter;
    private FormatEditTextView view;

    @Before
    public void setUp() {
        view = Mockito.mock(FormatEditTextView.class);
        presenter = new FormatEditTextPresenter(view);
    }

    @Test
    @Parameters({
            "$$ -- $$, $$ -- $$, 0 | 3",
            "$$ -- $$, $$ -- $$, 7 | 5",
            "$$ -- $$, , 4 | 0"
    })
    public void shouldSetPossibleCursorPosition(String format, String input, int startSelection, int expectedCursorPosition) {
        presenter.setCursorPosition(startSelection, format, input);

        Mockito.verify(view).setCursorPosition(expectedCursorPosition);
    }

    @Test
    @Parameters({
            "$$ -- $$, $$ -- $$, 4 | -1"
    })
    public void shouldReturnMinus1_whenCursorIsInValidPosition(String format, String input, int startSelection, int expectedCursorPosition) {
        presenter.setCursorPosition(startSelection, format, input);

        Mockito.verify(view).setCursorPosition(expectedCursorPosition);
    }

    @Test
    public void shouldAddGivenInputFilterToExistingFilters() {
        InputFilter[] existingFilters = new InputFilter[0];
        InputFilter newFilter = Mockito.mock(InputFilter.class);
        ArgumentCaptor<InputFilter[]> captor = ArgumentCaptor.forClass(InputFilter[].class);

        presenter.addInputFilterTo(existingFilters, newFilter);

        Mockito.verify(view).setFilters(captor.capture());
        assertEquals(existingFilters.length + 1, captor.getValue().length);
    }

    @Test
    public void shouldRemoveGivenInputFilterFromExistingFilters() {
        InputFilter[] existingFilters = new InputFilter[1];
        InputFilter newFilter = Mockito.mock(InputFilter.class);
        existingFilters[0] = newFilter;
        ArgumentCaptor<InputFilter[]> captor = ArgumentCaptor.forClass(InputFilter[].class);

        presenter.removeInputFilterTo(existingFilters, newFilter);

        Mockito.verify(view).setFilters(captor.capture());
        assertEquals(existingFilters.length - 1, captor.getValue().length);
    }
}