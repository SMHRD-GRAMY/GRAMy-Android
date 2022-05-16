package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.CommentsAdapter;
import com.example.gramy.Vo_Info.CommentVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity {

    RequestQueue queue;
    CommentsAdapter adapter = new CommentsAdapter();
    ArrayList<CommentVO> items = new ArrayList<CommentVO>();
    TextView tvCommentsBack, tvCommentsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        queue = Volley.newRequestQueue(CommentsActivity.this);

        tvCommentsBack = findViewById(R.id.tvCommentsBack);
        tvCommentsTitle = findViewById(R.id.tvCommentsTitle);

        RecyclerView recyclerView = findViewById(R.id.commentsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();

        tvCommentsTitle.setText(intent.getStringExtra("tb_a_title"));
        int tb_a_seq = intent.getIntExtra("tb_a_seq", 0);

        getCommentData(tb_a_seq);

        tvCommentsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void getCommentData (int tb_a_seq) {
        int method = Request.Method.POST;
        String server_url = "http://211.48.228.51:8082/app/replylist";
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
                        CommentVO item = new CommentVO(ar_seq, tb_a_seq, ar_content, user_id, user_name, ar_date);
                        items.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
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
}