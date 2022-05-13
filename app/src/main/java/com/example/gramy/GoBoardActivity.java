package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.gramy.Adapter.BoardAdapter;
import com.example.gramy.Vo_Info.BoardVO;

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

        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어답터 올려주기!
    }
}