package com.widgets.edittextformatter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        FormatEditText editText = findViewById(R.id.ed_text);
        editText.setFormat("+91 ----- -----");
        //editText.setValidator(new EvenNumberValidator(), new ValidationListener());

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
