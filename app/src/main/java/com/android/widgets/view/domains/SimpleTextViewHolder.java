package com.android.widgets.view.domains;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class SimpleTextViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvText;

    SimpleTextViewHolder(View view) {
        super(view);
        tvText = view.findViewById(android.R.id.text1);
    }

    TextView getTextView() {
        return tvText;
    }
}
