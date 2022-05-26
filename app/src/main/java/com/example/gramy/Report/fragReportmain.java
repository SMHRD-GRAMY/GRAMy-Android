package com.example.gramy.Report;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.GoreportAdapter;
import com.example.gramy.Listener.OnReportButtonClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.GoreportVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragReportmain extends Fragment {

    RequestQueue queue;
    GoreportAdapter adapter = new GoreportAdapter();
    ArrayList<GoreportVO> items = new ArrayList<GoreportVO>();
    int shelf_seq;
    RecyclerView rcvShelf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        rcvShelf = view.findViewById(R.id.rcvShelf);

        queue = Volley.newRequestQueue(getActivity());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sf_login", Context.MODE_PRIVATE);
        String loginName = sharedPreferences.getString("user_name", "");
        String loginId = sharedPreferences.getString("user_id","");

        getShelfData(loginId);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvShelf.setLayoutManager(LayoutManager);

        adapter.setOnItemClickListener(new OnReportButtonClickListener() {
            @Override
            public void onButtonClick(GoreportAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), com.example.gramy.Report.reportcheckActivity.class);
                System.out.println(shelf_seq);
                intent.putExtra("shelf_seq",shelf_seq);
                System.out.println(shelf_seq);
                startActivity(intent);
            }
        });

        rcvShelf.setAdapter(adapter);



        return view;
    }

    public void getShelfData (String loginId) {
        int method = Request.Method.POST;
        String server_url = "http://119.200.31.80:8082/product/shelflist";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(items.size()==0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject listItem = jsonArray.getJSONObject(i);
                            shelf_seq = listItem.getInt("shelf_seq");
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
                }else{

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