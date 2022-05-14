package com.example.gramy;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.Adapter.BoardAdapter;
import com.example.gramy.Listener.OnBoardItemClickListener;
import com.example.gramy.Vo_Info.BoardVO;

import java.util.ArrayList;

public class GoBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_board); // 게시판 액티비티
        RecyclerView recyclerView = findViewById(R.id.boardRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        BoardAdapter adapter = new BoardAdapter();

        adapter.addItem(new BoardVO("홍길동", "홍길동의 게시글", "2022-05-13"));
        adapter.addItem(new BoardVO("김유신", "김유신의 게시글", "2022-05-13"));
        adapter.addItem(new BoardVO("이순신", "이순신의 게시글", "2022-05-13"));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback); // ItemTouchHelper
        itemTouchHelper.attachToRecyclerView(recyclerView); // ItemTouchHelper

        // setOn"Item"ClickListener
        adapter.setOnItemClickListener(new OnBoardItemClickListener() {
            // 게시판 아이템 클릭했을 때의 로직 설정하기
            @Override
            public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position) {
                BoardVO item = adapter.getItem(position);
                Toast.makeText(GoBoardActivity.this, item.getUser_id(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어답터 올려주기!
    }

    // ItemTouchHelper
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        ArrayList<BoardVO> items = new ArrayList<BoardVO>();
        BoardAdapter adapter = new BoardAdapter();

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            items.remove(viewHolder.getLayoutPosition());
            adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
        }
    };
    // End of ItemTouchHelper


}