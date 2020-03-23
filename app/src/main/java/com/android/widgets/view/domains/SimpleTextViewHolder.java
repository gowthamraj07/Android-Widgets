package com.android.widgets.view.domains;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.widgets.R;

class SimpleTextViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvText;

    SimpleTextViewHolder(View view) {
        super(view);
        tvText = view.findViewById(R.id.tv_text);
    }

    TextView getTextView() {
        return tvText;
    }
}
