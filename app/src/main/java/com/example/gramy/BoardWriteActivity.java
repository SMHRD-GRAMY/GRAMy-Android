package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BoardWriteActivity extends AppCompatActivity {

    RequestQueue queue;
    TextView boardWriteBack, tvWriteBoard;
    EditText edtWriteTitle, edtWriteContent;
    Button btnWrtieCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        boardWriteBack = findViewById(R.id.boardWriteBack); // "뒤로"
        tvWriteBoard = findViewById(R.id.tvWriteBoard); // "게시물 작성"
        edtWriteTitle = findViewById(R.id.edtWriteTitle); // 게시글 제목 입력
        edtWriteContent = findViewById(R.id.edtWriteContent); // 게시글 본문 입력

        queue = Volley.newRequestQueue(BoardWriteActivity.this);

        String inputTitle = edtWriteTitle.getText().toString();
        String inputContent = edtWriteTitle.getText().toString();

        // 현재 로그인 한 유저 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);


        tvWriteBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 게시글 등록 로직 쓰기
                WriteBoard();
                Intent intent = new Intent(getApplicationContext(), GoBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

    public void WriteBoard () {
        int method = Request.Method.POST;
        String server_url = "http://211.48.228.51:8082/app/insert";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // 보낼 데이터
                // 1. title, content, user_id, user_name
                Map<String, String> param = new HashMap<>();
                // param.put();
                return super.getParams();
            }
        };
    }
}