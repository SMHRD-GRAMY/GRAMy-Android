package com.example.gramy.Listener;

import android.view.View;

import com.example.gramy.Adapter.BoardAdapter;

public interface OnBoardItemClickListener {
        // onItemClick 메서드가 호출될 때 파라미터로 뷰홀더 객체와 뷰 객체 그리고 뷰의 position 정보가 전달되도록 함
        // * position 정보는 몇 번째 아이템인지 구분할 수 있는 인덱스
        public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position);
}
