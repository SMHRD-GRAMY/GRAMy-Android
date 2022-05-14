package com.example.gramy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.BoardAdapter;
import com.example.gramy.Listener.OnBoardItemClickListener;
import com.example.gramy.Vo_Info.BoardVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class GoBoardActivity extends AppCompatActivity {

    RequestQueue queue;
    BoardAdapter adapter = new BoardAdapter();
    ArrayList<BoardVO> items = new ArrayList<BoardVO>();
    TextView boardBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_board); // 게시판 액티비티

        boardBack = findViewById(R.id.boardBack); // 뒤로가기

        queue = Volley.newRequestQueue(GoBoardActivity.this); // GoBoardActivity에 Queue 생성

        getBoardDate();

        RecyclerView recyclerView = findViewById(R.id.boardRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

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

        boardBack.setOnClickListener(new View.OnClickListener() {
            // "뒤로" 텍스트 눌렀을 때
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어답터 올려주기!
    }

    // ItemTouchHelper
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

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

    // 게시글 가져오는 메서드
    private void getBoardDate() {
        int method = Request.Method.GET;
        String server_url = "http://211.48.228.51:8082/app/list";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject listItem = jsonArray.getJSONObject(i);
                        int tb_a_seq = listItem.getInt("tb_a_seq");
                        String tb_a_title = listItem.getString("tb_a_title");
                        String tb_a_content = listItem.getString("tb_a_content");
                        String tb_a_date = listItem.getString("tb_a_date");
                        String user_id = listItem.getString("user_id");
                        String user_name = listItem.getString("user_name");
                        BoardVO item = new BoardVO(tb_a_seq, tb_a_title, tb_a_content, tb_a_date, user_id, user_name);
                        items.add(item);
                    }
                    System.out.println(items);
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }
}