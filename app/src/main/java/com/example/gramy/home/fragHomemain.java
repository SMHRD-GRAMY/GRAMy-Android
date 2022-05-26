package com.example.gramy.home;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SymbolTable;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.R;
import com.example.gramy.RegisterShelfActivity;
import com.example.gramy.StockActivity;
import com.example.gramy.StockCheckActivity;
import com.example.gramy.Vo_Info.ShelfStockVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class fragHomemain extends Fragment {

    RequestQueue queue;
    ArrayList<ShelfStockVO> items = new ArrayList<ShelfStockVO>();
    ArrayList<Integer> stock_seq_list= new ArrayList<Integer>();
    Button[] btnStock=new Button[4];
    View view;
    // 화면 설계 후 버튼 누르면 화면이동!
    TextView tvShelfTitle;
    Button btnShelfRegister;
    int shelf_seq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_homemain, container, false);

        tvShelfTitle = view.findViewById(R.id.tvShelfTitle);
        btnShelfRegister = view.findViewById(R.id.btnShelfRegister);
        queue = Volley.newRequestQueue(getContext());


        // 현재 로그인 한 유저 정보 가져오기
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("sf_login", Context.MODE_PRIVATE);
        String writerName = sharedPreferences.getString("user_name", "");
        String writerId = sharedPreferences.getString("user_id","");


        //액티비티에서 프래그먼트로 전달되는 데이터 가져오기
        Bundle bundle = getArguments();
        if(bundle != null){
            shelf_seq = bundle.getInt("shelf_seq");
        }

        // default 값을 0으로 해서 0일 경우 아이디를 통해 가져오고 아닐경우 선반 번호를 통해 가져오기
        if(shelf_seq==0) {
            getStockListFromId(writerId, view);

        }else{
            getStockListFromSeq(shelf_seq,view);
        }






        btnShelfRegister.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getContext(), RegisterShelfActivity.class);
                        startActivity(intent);
                    }
                }) ;
        //스위치 기능을 통한 라즈베리파이 작동
        return view;
    }
    // 목록 가져오는 메서드 아이디를 통해
    private void getStockListFromId(String writerId, View view) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/product/stocklist";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if(items.size()==0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject listItem = jsonArray.getJSONObject(i);
                            int shelf_seq = listItem.getInt("shelf_seq");
                            String shelf_name = listItem.getString("shelf_name");
                            String user_id = listItem.getString("user_id");
                            int stock_seq = listItem.getInt("stock_seq");
                            String stock_name = listItem.getString("stock_name");
                            ShelfStockVO item = new ShelfStockVO(shelf_seq, shelf_name, user_id, stock_seq, stock_name);
                            stock_seq_list.add(stock_seq);
                            items.add(item);
                        }
                    }else{
                    }

                    //물품 이름 리스트 배열 만들기
                    ArrayList<String> nameList=new ArrayList<String>();
                    //버튼 아이디 리스트 만들기

                    if(items.size()==0){
                        tvShelfTitle.setText("선반이 존재하지 않습니다");
                        Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
                        for(int i=0; i<4; i++) {
                            String buttonID = "btnStock" + (i+1);
                            int resID=getResources().getIdentifier(buttonID,"id",getActivity().getPackageName());
                            btnStock[i]=(Button)getView().findViewById(resID);
                            btnStock[i].setText("물품등록");
                            btnStock[i].setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(), "선반을 먼저 등록해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }) ;
                        }
                    }
                    //가져온 데이터 크기가 있는지 여부 판단
                    if (items.size() > 0) {

                        //선반 번호 가져오기
                        int shelf_seq=items.get(0).getShelf_seq();
                        Log.d("v", "test"+shelf_seq);
                        //선반 이름 가져오기
                        String shelfName = items.get(0).getShelf_name();
                        Log.d("v", "test"+shelfName);
                        tvShelfTitle.setText(shelfName);
                        //선반에 있는 물품 이름 리스트에 담아주기
                        for(int i=0;i<items.size();i++){
                            if(items.get(i).getStock_name().equals("null")){
                                items.get(i).setStock_name("물품등록");
                            }
                            System.out.println(items.get(i).getStock_name());
                            nameList.add(items.get(i).getStock_name());
                            if(items.get(0).getStock_name().equals("물품등록")){
                                System.out.println(items.get(0).getStock_name());
                                items.clear();
                            }
                        };
                        for(int i=0; i<items.size(); i++) {
                            String buttonID = "btnStock" + (i+1);
                            int resID=getResources().getIdentifier(buttonID,"id",getActivity().getPackageName());
                            int stock_seq=items.get(i).getStock_seq();
                            btnStock[i]=(Button)getView().findViewById(resID);
                            btnStock[i].setText(nameList.get(i));
                            //버튼클릭시 이벤트
                            int id=i;
                            btnStock[i].setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(getContext(), StockCheckActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("shelf_seq",shelf_seq);
                                    intent.putExtra("stock_seq",stock_seq);
                                    startActivity(intent);
                                }
                            }) ;
                        }
                        for(int i=items.size();i<4;i++){
                            String buttonID = "btnStock" + (i+1);
                            int resID=getResources().getIdentifier(buttonID,"id",getActivity().getPackageName());
                            btnStock[i]=(Button)getView().findViewById(resID);
                            btnStock[i].setText("물품등록");
                            int id=i;
                            btnStock[i].setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(getContext(), StockActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("shelf_seq",shelf_seq);
                                    startActivity(intent);

                                }
                            }) ;
                        }
                        if(stock_seq_list.size()!=0) {
                            for (int i=0; i<stock_seq_list.size(); i++){
                                setStockDeivce(i,stock_seq_list.get(i));
                            }
                        }
                        tvShelfTitle.setText(shelfName);
                    } else {

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
    //목록 가져오는 메서드 선반 번호를 통해
    private void getStockListFromSeq(int shelf_seq, View view) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/product/returnlist";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    System.out.println(jsonArray);
                    if(items.size()==0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject listItem = jsonArray.getJSONObject(i);
                            int shelf_seq = listItem.getInt("shelf_seq");
                            String shelf_name = listItem.getString("shelf_name");
                            String user_id = listItem.getString("user_id");
                            int stock_seq = listItem.getInt("stock_seq");
                            String stock_name = listItem.getString("stock_name");
                            stock_seq_list.add(stock_seq);
                            ShelfStockVO item = new ShelfStockVO(shelf_seq, shelf_name, user_id, stock_seq, stock_name);
                            stock_seq_list.add(stock_seq);
                            items.add(item);
                        }
                    }else{

                    }
                    //물품 이름 리스트 배열 만들기
                    ArrayList<String> nameList=new ArrayList<String>();
                    //버튼 아이디 리스트 만들기

                    //선반이 먼저 존재하지 않는경우
                    if(items.size()==0){
                        tvShelfTitle.setText("선반이 존재하지 않습니다");
                        Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
                        for(int i=0; i<4; i++) {
                            String buttonID = "btnStock" + (i+1);
                            int resID=getResources().getIdentifier(buttonID,"id",getActivity().getPackageName());
                            btnStock[i]=(Button)getView().findViewById(resID);
                            btnStock[i].setText("물품등록");
                            btnStock[i].setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(), "선반을 먼저 등록해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }) ;
                        }
                    }
                    //가져온 데이터 크기가 있는지 여부 판단
                    if (items.size() > 0) {
                        System.out.println(items);
                        //선반 번호 가져오기
                        int shelf_seq=items.get(0).getShelf_seq();
                        Log.d("v", "test"+shelf_seq);
                        //선반 이름 가져오기
                        String shelfName = items.get(0).getShelf_name();
                        Log.d("v", "test"+shelfName);
                        tvShelfTitle.setText(shelfName);
                        //선반에 있는 물품 이름 리스트에 담아주기
                        for(int i=0;i<items.size();i++){
                            System.out.println(items.get(i).getStock_name());
                            nameList.add(items.get(i).getStock_name());
                        };
                        if(items.get(0).getStock_name().equals("물품등록")){
                            System.out.println(items.get(0).getStock_name());
                            items.clear();
                        }
                        for(int i=0; i<items.size(); i++) {
                            String buttonID = "btnStock" + (i+1);
                            int resID=getResources().getIdentifier(buttonID,"id",getActivity().getPackageName());
                            int stock_seq=items.get(i).getStock_seq();
                            btnStock[i]=(Button)getView().findViewById(resID);
                            btnStock[i].setText(nameList.get(i));
                            //버튼클릭시 이벤트
                            int id=i;
                            btnStock[i].setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(getContext(), StockCheckActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("shelf_seq",shelf_seq);
                                    intent.putExtra("stock_seq",stock_seq);
                                    startActivity(intent);
                                }
                            }) ;
                        }
                        for(int i=items.size();i<4;i++){
                            String buttonID = "btnStock" + (i+1);
                            int resID=getResources().getIdentifier(buttonID,"id",getActivity().getPackageName());
                            btnStock[i]=(Button)getView().findViewById(resID);
                            System.out.println(items);
                            System.out.println(btnStock);
                            System.out.println(btnStock[0]);
                            btnStock[i].setText("물품등록");
                            int id=i;
                            btnStock[i].setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(getContext(), StockActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("shelf_seq",shelf_seq);
                                    startActivity(intent);
                                }
                            }) ;
                        }
                        if(stock_seq_list.size()!=0) {
                            for (int i=0; i<stock_seq_list.size(); i++){
                                setStockDeivce(i,stock_seq_list.get(i));
                            }
                        }

                    } else {

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
                param.put("shelf_seq", String.valueOf(shelf_seq));
                return param;
            }
        };
        queue.add(request);
    }

    private void setStockDeivce(int id,int stock_seq){
        int method = Request.Method.GET;
        String server_url = "http://172.30.1.40:8083/run/"+id+"?stock_seq="+stock_seq;// 하드웨어 url
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "제품세팅오류", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

}