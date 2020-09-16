package com.androidwidgets.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidwidgets.formatedittext.domain.Currency;
import com.androidwidgets.formatedittext.formatter.CurrencyFormatter;
import com.androidwidgets.formatedittext.utils.FormatTextWatcher;
import com.androidwidgets.formatedittext.widgets.FormatEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class DashFormatterFragment extends Fragment {

    private TextInputLayout textInputLayout1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash_formatter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputLayout1 = view.findViewById(R.id.text_input_layout_1);

        FormatEditText editText1 = view.findViewById(R.id.ed_text1);
        editText1.setFormat("----"); // 4 digit Number
        editText1.setValidator(new EvenNumberValidator(), new ValidationListener());

        FormatEditText editText2 = view.findViewById(R.id.ed_text2);
        editText2.setFormat("---- ---- ---- ----"); // VISA Card

        FormatEditText editText3 = view.findViewById(R.id.ed_text3);
        editText3.setFormat("---- ------ -----"); // American Express Card

        FormatEditText editText4 = view.findViewById(R.id.ed_text4);
        editText4.setFormat("--/---/----"); // Date in dd/MMM/yyyy format

        FormatEditText editText5 = view.findViewById(R.id.ed_text5);
        editText5.setFormat("(---) --- ----"); // Telephone number format of Japan (0AA) NXX XXXX

        FormatEditText editText6 = view.findViewById(R.id.ed_text6);
        Currency currency = new Currency("# ### ###,##", ",", 2);
        editText6.setFormatter(new CurrencyFormatter(currency));
    }

    private static class EvenNumberValidator implements FormatTextWatcher.Validator {
        @Override
        public boolean validate(String formattedUserInput, String unformattedUserInput) {
            if (unformattedUserInput == null || unformattedUserInput.isEmpty()) {
                return true;
            }

            return (Long.parseLong(unformattedUserInput) % 2) == 0;
        }
    }

    private class ValidationListener implements FormatTextWatcher.ValidationListener {
        @Override
        public void showSuccess() {
            textInputLayout1.setError("");
        }

        @Override
        public void showError() {
            textInputLayout1.setError("Not an even number");
        }

        @Override
        public void showEmpty() {
            textInputLayout1.setError("");
        }
    }
}
