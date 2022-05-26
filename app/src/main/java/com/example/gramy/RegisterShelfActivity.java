package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.ShelfAdapter;
import com.example.gramy.Listener.OnShelfButtonClickListener;
import com.example.gramy.Vo_Info.ShelfVO;
import com.example.gramy.home.fragHomemain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterShelfActivity extends AppCompatActivity {
        RequestQueue queue;
        ShelfAdapter adapter = new ShelfAdapter();
        ArrayList<ShelfVO> items = new ArrayList<ShelfVO>();
        Button buttonInsert,buttonCancel;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shelf_register);

            //버튼 초기화
            buttonInsert =findViewById(R.id.btnshelf_enroll);
            buttonCancel =findViewById(R.id.btnshelf_cancle);
            //queue 불러오기
            queue = Volley.newRequestQueue(RegisterShelfActivity.this);

            // 현재 로그인 한 유저 정보 가져오기
            SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);
            String user_name = sharedPreferences.getString("user_name", "");
            String user_id = sharedPreferences.getString("user_id","");
            //선반 목록 가져오기 아이디로부터 가져오기
            getShelfDataFromId(user_id);

            //리사이클러뷰 가져오기
            RecyclerView recyclerView =findViewById(R.id.rcvShelf);
            LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(LayoutManager);
            adapter.setOnItemClickListener(new OnShelfButtonClickListener() {
                @Override
                public void onButtonClick(ShelfAdapter.ViewHolder holder, View view, int position) {
                    ShelfVO item = adapter.getItem(position); // 각각의 게시글
                    int shelf_seq = item.getShelf_seq(); // 게시글 번호로 조회하기
                    System.out.println(shelf_seq);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("shelf_seq",shelf_seq);
                    startActivity(intent);
                }
            });

            recyclerView.setAdapter(adapter);

//
            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterShelfActivity.this);
                    View view = LayoutInflater.from(RegisterShelfActivity.this).inflate(R.layout.shelf_editbox, null, false);
                    builder.setView(view);

                    Button ButtonSubmitcancle = (Button) view.findViewById(R.id.shelf_button_dialog_submit_cancle);
                    Button ButtonSubmit = (Button) view.findViewById(R.id.shelf_button_dialog_submit);
                    EditText editTextName = (EditText) view.findViewById(R.id.shelf_edittext_dialog_name);

                    ButtonSubmit.setText("등록");

                    AlertDialog dialog = builder.create();


                    ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String shelf_name = editTextName.getText().toString();
                            ArrayList<ShelfVO> items = new ArrayList<ShelfVO>();
                            System.out.println(shelf_name);
                            System.out.println(user_id);
                            sendShelfName(user_id,shelf_name);


                            dialog.dismiss();
                        }
                    });

                    ButtonSubmitcancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }

            });

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        }
    public void getShelfDataFromId (String loginId) {
        int method = Request.Method.POST;
        String server_url = "http://121.147.52.210:8082/product/shelflist";
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
                        ShelfVO item = new ShelfVO(shelf_seq, shelf_name, user_id);
                        items.add(item);
                    }
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
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", loginId);
                return params;
            }
        };
        queue.add(request);
    }
    public void sendShelfName (String loginId,String shelf_name) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/product/insertshelf";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ShelfVO item = new ShelfVO(shelf_name);
                items.add(item);
                adapter.setItems(items);
                adapter.notifyDataSetChanged();

                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterShelfActivity.this, "등록실패", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("shelf_name",shelf_name);
                params.put("user_id", loginId);
                return params;
            }
        };
        queue.add(request);
    }
}