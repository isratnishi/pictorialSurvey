package com.opus_bd.pictorialsurvey.Activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opus_bd.pictorialsurvey.R;

public class DataViewHolder extends RecyclerView.ViewHolder {
    public TextView teamone,teamtwo;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        teamone = itemView.findViewById(R.id.teamone);
        teamtwo = itemView.findViewById(R.id.teamtwo);

    }
}