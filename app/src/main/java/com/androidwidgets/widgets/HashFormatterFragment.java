package com.androidwidgets.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidwidget.formatedittext.formatter.HashFormatter;
import com.androidwidget.formatedittext.widgets.FormatEditText;

public class HashFormatterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hash_formatter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FormatEditText etDate = view.findViewById(R.id.et_date);
        etDate.setFormatter(new HashFormatter("##-###-####"));
        etDate.setHint("dd-MMM-yyyy");
    }
}
