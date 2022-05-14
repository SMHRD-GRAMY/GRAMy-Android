package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BoardWriteActivity extends AppCompatActivity {

    TextView boardWriteBack;
    Button btnWrtieCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        boardWriteBack = findViewById(R.id.boardWriteBack);

        // 게시글 목록으로 이동
        boardWriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}