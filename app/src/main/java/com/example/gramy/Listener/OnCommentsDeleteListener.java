package com.example.gramy.Listener;

import android.view.View;

import com.example.gramy.Adapter.CommentsAdapter;

public interface OnCommentsDeleteListener {
    public void onItemDelete(CommentsAdapter.ViewHolder holder, View view, int position);
}
