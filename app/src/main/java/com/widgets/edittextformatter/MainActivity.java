package com.widgets.edittextformatter;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.widgets.edittextformatter.formatter.DashFormatter;
import com.widgets.edittextformatter.utils.FormatTextWatcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edFourDigitCode = findViewById(R.id.ed_four_digit_code);
        FormatTextWatcher.Formatter formatter = new DashFormatter();
        edFourDigitCode.addTextChangedListener(new FormatTextWatcher(edFourDigitCode, formatter));
    }
}
