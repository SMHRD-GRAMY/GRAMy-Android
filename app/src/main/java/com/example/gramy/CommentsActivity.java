package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.gramy.Adapter.CommentsAdapter;
import com.example.gramy.Listener.OnCommentsDeleteListener;
import com.example.gramy.Vo_Info.CommentVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity {

    RequestQueue queue;
    CommentsAdapter commentsAdapter = new CommentsAdapter();
    ArrayList<CommentVO> items = new ArrayList<CommentVO>();
    TextView tvCommentsBack, tvCommentsTitle, tvDeleteComment, tvEditComment;
    EditText edtComment;
    Button btnWriteComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);
        String writerName = sharedPreferences.getString("user_name", "");
        String writerId = sharedPreferences.getString("user_id","");

        queue = Volley.newRequestQueue(CommentsActivity.this);

        tvCommentsBack = findViewById(R.id.tvCommentsBack);
        tvCommentsTitle = findViewById(R.id.tvCommentsTitle);
        edtComment = findViewById(R.id.edtComment);
        btnWriteComment = findViewById(R.id.btnWriteComment);
        tvDeleteComment = findViewById(R.id.tvDeleteComment);


        RecyclerView recyclerView = findViewById(R.id.commentsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();

        tvCommentsTitle.setText(intent.getStringExtra("tb_a_title"));
        int tb_a_seq = intent.getIntExtra("tb_a_seq", 0);

        getCommentData(tb_a_seq);

        // 뒤로가기
        tvCommentsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 댓글 작성하기
        btnWriteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edtComment.getText().toString();
                writeComment(tb_a_seq, comment, writerId, writerName);
            }
        });

        // 댓글 삭제하기
        commentsAdapter.OnCommentsDeleteListener(new OnCommentsDeleteListener() {
            @Override
            public void onItemDelete(CommentsAdapter.ViewHolder holder, View view, int position) {
                int ar_seq = items.get(position).getAr_seq();
                deleteComment(ar_seq);
            }
        });

        recyclerView.setAdapter(commentsAdapter);
    }

    public void getCommentData (int tb_a_seq) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/app/replylist";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject listItem = jsonArray.getJSONObject(i);
                        int ar_seq = listItem.getInt("ar_seq");
                        int tb_a_seq = listItem.getInt("tb_a_seq");
                        String ar_content = listItem.getString("ar_content");
                        String user_id = listItem.getString("user_id");
                        String user_name = listItem.getString("user_name");
                        String ar_date = listItem.getString("ar_date").substring(0, 11);
                        CommentVO item = new CommentVO(tb_a_seq, ar_seq, ar_content, user_id, user_name, ar_date);
                        items.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commentsAdapter.setItems(items);
                commentsAdapter.notifyDataSetChanged();
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
                params.put("tb_a_seq", String.valueOf(tb_a_seq));
                return params;
            }
        };
        queue.add(request);
    }

    public void writeComment (int tb_a_seq, String comment, String user_id, String user_name) {
        int method = Request.Method.POST;
        String server_url = "http://211.48.228.51:8082/app/replyinsert";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")) {
                    Toast.makeText(CommentsActivity.this, "댓글이 작성되었습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    edtComment.setText("");
                } else {
                    Toast.makeText(CommentsActivity.this, "댓글 작성 중 오류 발생!", Toast.LENGTH_SHORT).show();
                }

                commentsAdapter.notifyDataSetChanged();
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
                params.put("tb_a_seq", String.valueOf(tb_a_seq));
                params.put("ar_content", String.valueOf(comment));
                params.put("user_id", String.valueOf(user_id));
                params.put("user_name", String.valueOf(user_name));
                return params;
            }
        };
        queue.add(request);
    }

    public void deleteComment (int ar_seq) {
        int method = Request.Method.POST;
        String server_url = "http://211.48.228.51:8082/app/replydelete";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")) {
                    Toast.makeText(CommentsActivity.this, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(CommentsActivity.this, "댓글 삭제중 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
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
                params.put("ar_seq", String.valueOf(ar_seq));
                return params;
            }
        };
        queue.add(request);
    }
}