package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.BoardAdapter;

import java.util.HashMap;
import java.util.Map;

public class BoardWriteActivity extends AppCompatActivity {

    RequestQueue queue;
    TextView boardWriteBack, tvWriteBoard;
    EditText edtWriteTitle, edtWriteContent;
    BoardAdapter adapter = new BoardAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        boardWriteBack = findViewById(R.id.tvCommentsBack); // "뒤로"
        tvWriteBoard = findViewById(R.id.tvUpdateBoard); // "게시물 작성"
        edtWriteTitle = findViewById(R.id.edtUpdateTitle); // 게시글 제목 입력
        edtWriteContent = findViewById(R.id.edtUpdateContent); // 게시글 본문 입력

        queue = Volley.newRequestQueue(BoardWriteActivity.this);

        // 현재 로그인 한 유저 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);
        String writerName = sharedPreferences.getString("user_name", "");
        String writerId = sharedPreferences.getString("user_id","");
        System.out.println(sharedPreferences.getAll());


        tvWriteBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 게시글 등록 로직 쓰기
                String inputTitle = edtWriteTitle.getText().toString();
                String inputContent = edtWriteContent.getText().toString();
                writeBoard(writerId, writerName, inputTitle, inputContent);
                finish();

            }
        });
        boardWriteBack = findViewById(R.id.tvCommentsBack);
        // 게시글 목록으로 이동
        boardWriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void writeBoard (String writerId, String writerName, String inputTitle, String inputContent) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/app/insert";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "게시물이 성공적으로 작성되었습니다!", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
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
                param.put("tb_a_title", inputTitle);
                param.put("tb_a_content", inputContent);
                param.put("user_id", writerId);
                param.put("user_name", writerName);
                return param;
            }
        };
        queue.add(request);
    }
}