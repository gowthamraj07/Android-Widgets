package com.widgets.edittextformatter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.widgets.edittextformatter.formatter.DashFormatter;
import com.widgets.edittextformatter.utils.FormatTextWatcher;

public class MainActivity extends AppCompatActivity implements FormatTextWatcher.ValidationListener {

    private TextInputLayout textInputLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputLayout1 = findViewById(R.id.text_input_layout_1);
        final EditText editText = findViewById(R.id.ed_four_digit_code);
        FormatTextWatcher.Formatter formatter = new DashFormatter("$$--$$");
        final FormatTextWatcher textWatcher = new FormatTextWatcher(editText, formatter, new EvenNumberValidator(), this);
        textWatcher.init();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textWatcher.setInitialText();
                    editText.addTextChangedListener(textWatcher);
                } else {
                    editText.removeTextChangedListener(textWatcher);
                }
            }
        });


    }

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

    private class EvenNumberValidator implements FormatTextWatcher.Validator {
        @Override
        public boolean validate(String formattedUserInput, String unformattedUserInput) {
            if (unformattedUserInput == null || unformattedUserInput.isEmpty()) {
                return true;
            }

            return (Integer.parseInt(unformattedUserInput) % 2) == 0;
        }
    }
}
