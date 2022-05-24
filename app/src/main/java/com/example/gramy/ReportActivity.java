package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.GoreportAdapter;
import com.example.gramy.Listener.OnReportButtonClickListener;
import com.example.gramy.Report.reportcheckActivity;
import com.example.gramy.Vo_Info.GoreportVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {

    RequestQueue queue;
    GoreportAdapter adapter = new GoreportAdapter();
    ArrayList<GoreportVO> items = new ArrayList<GoreportVO>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_report);

        RecyclerView recyclerView =findViewById(R.id.rcvShelf);

        SharedPreferences sharedPreferences = getSharedPreferences("sf_login", MODE_PRIVATE);
        String loginName = sharedPreferences.getString("user_name", "");
        String loginId = sharedPreferences.getString("user_id","");

        queue = Volley.newRequestQueue(ReportActivity.this);

        getShelfData(loginId);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        adapter.setOnItemClickListener(new OnReportButtonClickListener() {
            @Override
            public void onButtonClick(GoreportAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), reportcheckActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView.setAdapter(adapter);
        }

        public void getShelfData (String loginId) {
            int method = Request.Method.POST;
            String server_url = "http://172.30.1.52:8082/product/shelflist";
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
                            GoreportVO item = new GoreportVO(shelf_seq, shelf_name, user_id);
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



