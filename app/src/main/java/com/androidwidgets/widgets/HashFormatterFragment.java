package com.androidwidgets.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidwidgets.formatedittext.formatter.HashFormatter;
import com.androidwidgets.formatedittext.widgets.FormatEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
