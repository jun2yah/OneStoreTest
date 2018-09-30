package com.jun2yah.codingtest.onestoretest.view.servicelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jun2yah.codingtest.onestoretest.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewServiceName;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        textViewServiceName = (TextView) itemView.findViewById(R.id.textViewServiceName);
    }
}
