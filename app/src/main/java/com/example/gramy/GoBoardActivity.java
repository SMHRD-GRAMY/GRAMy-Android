//package com.example.gramy;
//
//
//
//
//import android.content.SharedPreferences;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import com.example.gramy.Adapter.BoardAdapter;
//import com.example.gramy.Listener.OnBoardItemClickListener;
//import com.example.gramy.Vo_Info.BoardVO;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class GoBoardActivity extends AppCompatActivity {
//
//    RequestQueue queue;
//    BoardAdapter adapter = new BoardAdapter();
//    ArrayList<BoardVO> items = new ArrayList<BoardVO>();
//    TextView boardBack;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_go_board); // 게시판 액티비티
//
//        boardBack = findViewById(R.id.tvBoardBack); // 뒤로가기
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab); // 플러팅 버튼
//
//        queue = Volley.newRequestQueue(GoBoardActivity.this); // GoBoardActivity에 Queue 생성
//
//        getBoardData(); // 게시글 목록 불러오기
//
//        RecyclerView recyclerView = findViewById(R.id.boardRecyclerView);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        // setOn"Item"ClickListener
//        adapter.setOnItemClickListener(new OnBoardItemClickListener() { // 인터페이스
//            // TODO: 게시글 상세보기 구현
//            @Override
//            public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position) {
//                BoardVO item = adapter.getItem(position); // 각각의 게시글
//                int BoardSeq = item.getTb_a_seq(); // 게시글 번호로 조회하기
//                String writerId = item.getUser_id();
//                System.out.println(BoardSeq);
//
//                // 액티비티 이동
//                Intent intent = new Intent(getApplicationContext(), BoardDetailActivity.class);
//                intent.putExtra("tb_a_seq", BoardSeq);
//                intent.putExtra("user_id", writerId);
//                startActivity(intent);
//                finish();
//                //
//            }
//        });
//
//        boardBack.setOnClickListener(new View.OnClickListener() {
//            // "뒤로" 텍스트 눌렀을 때
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), BoardWriteActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어답터 올려주기!
//    }
//
//    // 게시글 가져오는 메서드
//    private void getBoardData() {
//        int method = Request.Method.GET;
//        String server_url = "http://211.48.228.51:8082/app/list";
//        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for(int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject listItem = jsonArray.getJSONObject(i);
//                        int tb_a_seq = listItem.getInt("tb_a_seq");
//                        String tb_a_title = listItem.getString("tb_a_title");
//                        String tb_a_content = listItem.getString("tb_a_content");
//                        String tb_a_date = listItem.getString("tb_a_date");
//                        String user_id = listItem.getString("user_id");
//                        String user_name = listItem.getString("user_name");
//                        BoardVO item = new BoardVO(tb_a_seq, tb_a_title, tb_a_content, tb_a_date, user_id, user_name);
//                        items.add(item);
//                    }
//                    adapter.setItems(items);
//                    adapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        queue.add(request);
//    }
//}