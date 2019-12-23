package com.androidwidgets.formatedittext.presenter;

import android.text.InputFilter;

import com.androidwidgets.formatedittext.view.FormatEditTextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void shouldSetPossibleCursorPositionForTheFormat(String format, String input, int startSelection, int expectedCursorPosition) {
        presenter.getStartSelection(startSelection, format, input);

        Mockito.verify(view).setCursorPosition(expectedCursorPosition);
    }

    @Test
    public void shouldAddGivenInputFilterToExistingFilters() {
        InputFilter[] existingFilters = new InputFilter[0];
        InputFilter newFilter = Mockito.mock(InputFilter.class);

        InputFilter[] resultFilters = presenter.addInputFilterTo(existingFilters, newFilter);

        assertEquals(existingFilters.length + 1, resultFilters.length);
    }

    @Test
    public void shouldRemoveGivenInputFilterFromExistingFilters() {
        InputFilter[] existingFilters = new InputFilter[1];
        InputFilter newFilter = Mockito.mock(InputFilter.class);
        existingFilters[0] = newFilter;

        InputFilter[] resultFilters = presenter.removeInputFilterTo(existingFilters, newFilter);

        assertEquals(existingFilters.length - 1, resultFilters.length);
    }
}