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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.GoreportAdapter;
import com.example.gramy.Adapter.ShelfAdapter;
import com.example.gramy.Listener.OnReportButtonClickListener;
import com.example.gramy.Listener.OnShelfButtonClickListener;
import com.example.gramy.Vo_Info.ShelfVO;
import com.example.gramy.home.fragHomemain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import GoMgSelf.Dictionary;

public class RegisterShelfActivity extends AppCompatActivity {
        RequestQueue queue;
        ShelfAdapter adapter = new ShelfAdapter();
        ArrayList<ShelfVO> items = new ArrayList<ShelfVO>();
        Button buttonInsert,buttonCancel;
        ArrayList<Dictionary> mArrayList;
        int count = -1;
        fragHomemain fragHomemain;


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
            //선반 목록 가져오기
            getShelfData(user_id);

            //리사이클러뷰 가져오기
            RecyclerView recyclerView =findViewById(R.id.rcvShelf);
            LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(LayoutManager);
            adapter.setOnItemClickListener(new OnShelfButtonClickListener() {
                @Override
                public void onButtonClick(ShelfAdapter.ViewHolder holder, View view, int position) {
                    Intent intent = new Intent(getApplicationContext(), reportcheckActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            recyclerView.setAdapter(adapter);


            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterShelfActivity.this);
                    View view = LayoutInflater.from(RegisterShelfActivity.this).inflate(R.layout.shelf_editbox, null, false);
                    builder.setView(view);

                    final Button ButtonSubmitcancle = (Button) view.findViewById(R.id.shelf_button_dialog_submit_cancle);
                    final Button ButtonSubmit = (Button) view.findViewById(R.id.shelf_button_dialog_submit);
                    final EditText editTextName = (EditText) view.findViewById(R.id.shelf_edittext_dialog_name);


                    ButtonSubmit.setText("등록");

                    final AlertDialog dialog = builder.create();


                    ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // String strID =  editTextID.getText().toString();
                            String strName = editTextName.getText().toString();

                            Dictionary dict = new Dictionary(strName);
                            mArrayList.add(0, dict);

//                            mAdapter.notifyItemInserted(0);

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
    public void getShelfData (String loginId) {
        int method = Request.Method.POST;
        String server_url = "http://119.200.31.80:8082/product/shelflist";
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
                params.put("user_id", String.valueOf(loginId));
                return params;
            }
        };
        queue.add(request);
    }
}