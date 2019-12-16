package com.widgets.edittextformatter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.widgets.edittextformatter.utils.FormatTextWatcher;
import com.widgets.edittextformatter.widgets.FormatEditText;

public class MainActivity extends AppCompatActivity implements FormatTextWatcher.ValidationListener {

    private TextInputLayout textInputLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputLayout1 = findViewById(R.id.text_input_layout_1);
        final FormatEditText editText = findViewById(R.id.ed_four_digit_code);
        editText.setValidator(new EvenNumberValidator());
        editText.setValidationListener(new ValidationListener());
        editText.setFormat("$$ -- $$");
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    private class EvenNumberValidator implements FormatTextWatcher.Validator {
        @Override
        public boolean validate(String formattedUserInput, String unformattedUserInput) {
            if (unformattedUserInput == null || unformattedUserInput.isEmpty()) {
                return true;
            }

            return (Integer.parseInt(unformattedUserInput) % 2) == 0;
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
