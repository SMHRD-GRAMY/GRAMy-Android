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
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.BoardAdapter;
import com.example.gramy.Listener.OnBoardItemClickListener;
import com.example.gramy.Vo_Info.BoardVO;
import com.example.gramy.Vo_Info.ShelfStockVO;
import com.example.gramy.stock.StockActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoPdCheck extends AppCompatActivity {

    RequestQueue queue;
    ArrayList<ShelfStockVO> items = new ArrayList<ShelfStockVO>();
    TextView boardBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_board); // 게시판 액티비티

        boardBack = findViewById(R.id.tvBoardBack); // 뒤로가기
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab); // 플러팅 버튼

        queue = Volley.newRequestQueue(GoPdCheck.this); // GoBoardActivity에 Queue 생성

        // 현재 로그인 한 유저 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);
        String writerName = sharedPreferences.getString("user_name", "");
        String writerId = sharedPreferences.getString("user_id","");


        getStockList(writerId); // 게시글 목록 불러오기




    }

    // 게시글 가져오는 메서드
    private void getStockList(String writerId) {
        int method = Request.Method.GET;
        String server_url = "http://211.48.228.51:8082/product/stocklist";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject listItem = jsonArray.getJSONObject(i);
                        int shelf_seq = listItem.getInt("shelf_seq");
                        String shelf_name = listItem.getString("shelf_name");
                        String user_id = listItem.getString("user_id");
                        int stock_seq = listItem.getInt("stock_seq");
                        String stock_name = listItem.getString("stock_name");
                        ShelfStockVO item = new ShelfStockVO(shelf_seq, shelf_name, user_id, stock_seq, stock_name);
                        items.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // 보낼 데이터
                // 1. title, content, user_id, user_name
                Map<String, String> param = new HashMap<>();
                param.put("user_id", writerId);
                return param;
            }
        };

        queue.add(request);
    }
}