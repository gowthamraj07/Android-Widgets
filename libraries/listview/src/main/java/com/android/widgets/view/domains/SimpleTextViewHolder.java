package com.android.widgets.view.domains;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SimpleTextViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvText;

    public SimpleTextViewHolder(View view) {
        super(view);
        tvText = view.findViewById(android.R.id.text1);
    }

    public TextView getTextView() {
        return tvText;
    }
}
