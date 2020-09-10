package com.androidwidgets.widgets;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidwidgets.formatedittext.widgets.FormatEditText;


public class DashFormatterExampleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash_formatter_example, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView tvResultCode = view.findViewById(R.id.tvResultCode);
        final FormatEditText etOutput = view.findViewById(R.id.etOutput);
        EditText edFormat = view.findViewById(R.id.edFormat);
        edFormat.setFilters(getFilterForDashFormatter());
        edFormat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable format) {
                etOutput.setText("");
                String formatString = format.toString();
                etOutput.setFormat(formatString);

                String generatedCode = "\n" +
                        "final FormatEditText editText = findViewById(R.id.ed_text);\n" +
                        "editText.setFormat("+formatString+");";
                tvResultCode.setText(generatedCode);
            }
        });
    }

    private InputFilter[] getFilterForDashFormatter() {
        InputFilter[] inputFilters = new InputFilter[1];
        inputFilters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start;i < end;i++) {
                    char character = source.charAt(i);
                    if (!isValid(character)) {
                        return "";
                    }
                }
                return null;
            }
        };
        return inputFilters;
    }

    private boolean isValid(char character) {
        return Character.toString(character).equals(" ") ||
                Character.toString(character).equals("-");
    }
}