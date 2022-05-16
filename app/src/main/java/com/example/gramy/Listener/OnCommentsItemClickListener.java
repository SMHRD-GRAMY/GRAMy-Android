package com.example.gramy.Listener;

import android.view.View;

import com.example.gramy.Adapter.CommentsAdapter;

public interface OnCommentsItemClickListener {

    public void onItemClick(CommentsAdapter.ViewHolder holder, View view, int position);
}
