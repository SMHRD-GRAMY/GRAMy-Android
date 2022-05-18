package com.example.gramy.Listener;

import android.view.View;

import com.example.gramy.Adapter.GoreportAdapter;

public interface OnReportButtonClickListener {
    public void onButtonClick(GoreportAdapter.ViewHolder holder, View view, int position);
}
