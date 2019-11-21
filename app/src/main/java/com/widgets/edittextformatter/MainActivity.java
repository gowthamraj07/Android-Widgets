package com.widgets.edittextformatter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.widgets.edittextformatter.utils.FormatTextWatcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edFourDigitCode = findViewById(R.id.ed_four_digit_code);
        edFourDigitCode.addTextChangedListener(new FormatTextWatcher("----"));
    }
}
