package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class BoardUpdateActivity extends AppCompatActivity {

    int board_seq;
    RequestQueue queue;
    BoardAdapter adapter = new BoardAdapter();
    TextView tvUpdateBoard, tvBoardUpdateBack;
    EditText edtUpdateTitle, edtUpdateContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_update);

        tvUpdateBoard = findViewById(R.id.tvUpdateBoard); // 게시글 수정하기
        tvBoardUpdateBack = findViewById(R.id.tvCommentsBack); // 뒤로가기
        edtUpdateTitle = findViewById(R.id.edtUpdateTitle);
        edtUpdateContent = findViewById(R.id.edtUpdateContent);

        queue = Volley.newRequestQueue(BoardUpdateActivity.this);
        Intent intent = getIntent();

        board_seq = intent.getIntExtra("tb_a_seq", 0);

        edtUpdateTitle.setText(intent.getStringExtra("tb_a_title"));
        edtUpdateContent.setText(intent.getStringExtra("tb_a_content"));

        tvUpdateBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateBoard(board_seq, edtUpdateTitle.getText().toString(), edtUpdateContent.getText().toString());
                Intent intent = new Intent(getApplicationContext(), BoardDetailActivity.class);
                intent.putExtra("tb_a_seq", board_seq);
                adapter.notifyDataSetChanged();
                startActivity(intent);
                finish();
            }
        });

        tvBoardUpdateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void UpdateBoard (int board_seq, String title, String content) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/app/update";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tb_a_seq", String.valueOf(board_seq));
                params.put("tb_a_title", title);
                params.put("tb_a_content",content);
                return params;
            }
        };
        queue.add(request);
    }
}