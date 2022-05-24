package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.gramy.Community.fragNewsmain;
import com.example.gramy.Vo_Info.BoardVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BoardDetailActivity extends AppCompatActivity {

    RequestQueue queue;
    int board_seq;
    String writerId;
    TextView tvBoardDetailBack, tvBoardDetailTitle, tvBoardDetailWriter, tvBoardDetailDate, tvBoardDetailModify, tvBoardDetailDelete, tvBoardDetailContent;
    BoardAdapter adapter = new BoardAdapter();
    Button btnViewComment;
    fragNewsmain fragNewsmain = new fragNewsmain();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        tvBoardDetailBack = findViewById(R.id.tvBoardDetailBack); // "목록으로 돌아가기"
        tvBoardDetailTitle = findViewById(R.id.tvBoardDetailTitle); // 게시글 제목
        tvBoardDetailWriter = findViewById(R.id.tvBoardDetailWriter); // 게시글 작성자
        tvBoardDetailContent = findViewById(R.id.tvBoardDetailContent); // 게시글 본문
        tvBoardDetailDate = findViewById(R.id.tvBoardDetailDate); // 게시글 작성일자
        tvBoardDetailModify = findViewById(R.id.tvBoardDetailModify); // "게시글 수정"
        tvBoardDetailDelete = findViewById(R.id.tvBoardDetailDelete); // "게시글 삭제"
        btnViewComment = findViewById(R.id.btnViewComment); // 댓글 보기 버튼

        queue = Volley.newRequestQueue(BoardDetailActivity.this);
        Intent intent = getIntent();

        board_seq = intent.getIntExtra("tb_a_seq", 0);
        writerId = intent.getStringExtra("user_id");

        getData(board_seq);

        // 현재 로그인 한 유저 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);
        String loginName = sharedPreferences.getString("user_name", "");
        String loginId = sharedPreferences.getString("user_id","");

        System.out.println("로그인 아이디 : " + loginId);
        System.out.println("게시글 작성자 아이디 : " + writerId);

        if(writerId.equals(loginId)) {
            tvBoardDetailModify.setVisibility(View.VISIBLE);
            tvBoardDetailDelete.setVisibility(View.VISIBLE);
        } else {
            tvBoardDetailModify.setVisibility(View.INVISIBLE);
            tvBoardDetailDelete.setVisibility(View.INVISIBLE);
        }

        // 뒤로가기
        tvBoardDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 게시글 삭제
        tvBoardDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBoard(board_seq);
            }
        });

        // 게시글 수정 페이지로 이동
        tvBoardDetailModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardUpdateActivity.class);
                intent.putExtra("tb_a_seq", board_seq); // 수정할 게시글의 번호
                intent.putExtra("tb_a_title", tvBoardDetailTitle.getText().toString());
                intent.putExtra("tb_a_content", tvBoardDetailContent.getText().toString());
                startActivity(intent);
            }
        });

        // 댓글 보기
        btnViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
            intent.putExtra("tb_a_seq", board_seq);
            intent.putExtra("tb_a_title", tvBoardDetailTitle.getText().toString());
            startActivity(intent);
            }
        });
    }


    public void getData(int board_seq) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/app/detail";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tvBoardDetailTitle.setText(jsonObject.getString("tb_a_title"));
                    tvBoardDetailWriter.setText("작성자 : " + jsonObject.getString("user_name"));
                    tvBoardDetailDate.setText(jsonObject.getString("tb_a_date").substring(0, 11));
                    tvBoardDetailContent.setText(jsonObject.getString("tb_a_content"));
                } catch (Exception e) {
                    e.printStackTrace();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("tb_a_seq", String.valueOf(board_seq));
                return params;
            }
        };
        queue.add(request);
    }

    public void deleteBoard (int board_seq) {
        int method = Request.Method.POST;
        String server_url = "http://211.48.228.51:8082/app/delete";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.equals("success")) {
                        Toast.makeText(getApplicationContext(), "게시글이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragNewsmain).commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("tb_a_seq", String.valueOf(board_seq));
                return params;
            }
        };
        queue.add(request);
    }
}