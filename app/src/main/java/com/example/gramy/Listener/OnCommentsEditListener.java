package com.example.gramy.Listener;

import android.view.View;

import com.example.gramy.Adapter.CommentsAdapter;

public interface OnCommentsEditListener {

    public void onEditItem(CommentsAdapter.ViewHolder holder, View view, int position);
}
