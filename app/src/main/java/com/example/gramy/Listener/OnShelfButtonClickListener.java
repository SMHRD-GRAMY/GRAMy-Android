package com.example.gramy.Listener;

import android.view.View;

import com.example.gramy.Adapter.ShelfAdapter;


public interface OnShelfButtonClickListener {
    public void onButtonClick(ShelfAdapter.ViewHolder holder, View view, int position);
}
