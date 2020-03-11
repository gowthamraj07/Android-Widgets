package com.android.widgets;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidwidgets.formatedittext.domain.Currency;
import com.androidwidgets.formatedittext.formatter.CurrencyFormatter;
import com.androidwidgets.formatedittext.utils.FormatTextWatcher;
import com.androidwidgets.formatedittext.widgets.FormatEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputLayout1 = findViewById(R.id.text_input_layout_1);

        FormatEditText editText1 = findViewById(R.id.ed_text1);
        editText1.setFormat("----"); // 4 digit Number
        editText1.setValidator(new EvenNumberValidator(), new ValidationListener());

        FormatEditText editText2 = findViewById(R.id.ed_text2);
        editText2.setFormat("---- ---- ---- ----"); // VISA Card

        FormatEditText editText3 = findViewById(R.id.ed_text3);
        editText3.setFormat("---- ------ -----"); // American Express Card

        FormatEditText editText4 = findViewById(R.id.ed_text4);
        editText4.setFormat("--/---/----"); // Date in dd/MMM/yyyy format

        FormatEditText editText5 = findViewById(R.id.ed_text5);
        editText5.setFormat("(---) --- ----"); // Telephone number format of Japan (0AA) NXX XXXX

        FormatEditText editText6 = findViewById(R.id.ed_text6);
        Currency currency = new Currency("# ### ###,##", ",", 2);
        editText6.setFormatter(new CurrencyFormatter(currency));

    }

    private class EvenNumberValidator implements FormatTextWatcher.Validator {
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
