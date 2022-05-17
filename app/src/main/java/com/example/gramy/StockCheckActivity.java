package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Join_Login.LoginActivity;
import com.example.gramy.Vo_Info.GramyUserVO;
import com.example.gramy.Vo_Info.ShelfStockVO;
import com.example.gramy.Vo_Info.StockDetailVO;
import com.example.gramy.Vo_Info.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockCheckActivity extends AppCompatActivity {

    ArrayList<StockDetailVO> items = new ArrayList<StockDetailVO>();
    RequestQueue queue;
    Button button;
    ProgressBar progressBar;
    EditText checkWeightTv,checkNameTv,checkRegisterTv,checkShelfLifeTv,checkOrderTv;
    TextView checkPercentTv;
    int firstWeight,secondWeight;
    int stock_seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check);

        //초기화
        progressBar = (ProgressBar)findViewById(R.id.progressBar2); // 프로그래스바
        checkWeightTv = (EditText)findViewById(R.id.checkWeightTv);// 현재 무게
        checkNameTv=findViewById(R.id.checkNameTv);
        checkRegisterTv=findViewById(R.id.checkRegisterTv);
        checkShelfLifeTv=findViewById(R.id.checkSelfLifeTv);
        checkOrderTv=findViewById(R.id.checkOrderTv);
        checkPercentTv = (TextView) findViewById(R.id.checkPercentTv); //퍼센트뷰
        button = (Button)findViewById(R.id.checkModifyBtn);


        //큐초기화
        queue = Volley.newRequestQueue(StockCheckActivity.this);
        //인텐트를 통한 선반 번호 가져오기
        Intent intent=getIntent();
        stock_seq=intent.getIntExtra("stock_seq",0);
        getDetail(stock_seq);

    }



    // 목록 가져오는 메서드
    private void getDetail(int stock_seq) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.42:8082/product/detailItem";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 1) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int stock_seq = jsonObject.getInt("stock_seq");;
                        String stock_name = jsonObject.getString("stock_name");
                        int stock_weight = jsonObject.getInt("stock_weight");
                        String stock_date = jsonObject.getString("stock_date");
                        String stock_shelfLife = jsonObject.getString("stock_shelfLife");
                        int shelf_seq = jsonObject.getInt("shelf_seq");
                        String stock_order=jsonObject.getString("stock_order");
                        int weight_value = jsonObject.getInt("weight_value");
                        String weight_date=jsonObject.getString("weight_date");
                        StockDetailVO item = new StockDetailVO(stock_seq,stock_name,stock_weight,stock_date,stock_shelfLife,shelf_seq,stock_order,weight_value,weight_date);
                        items.add(item);

                        checkNameTv.setText(items.get(0).getStock_name());
                        checkWeightTv.setText(items.get(0).getWeight_value()+"/"+items.get(0).getStock_weight());
                        checkRegisterTv.setText(items.get(0).getStock_date());
                        checkShelfLifeTv.setText(items.get(0).getStock_shelfLife());
                        checkOrderTv.setText(items.get(0).getStock_order());


                        String str=checkWeightTv.getText().toString();
                        String[] array=str.split("/");

                        secondWeight= Integer.parseInt(array[0]);
                        System.out.println(secondWeight);
                        firstWeight=Integer.parseInt(array[1]);
                        System.out.println(firstWeight);
                        double result=((double)secondWeight/firstWeight)*100;
                        int result1=(int)result;
                        System.out.println(result);

                        if(result<=10){
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                        } else {
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                        }
                        String currentWeight=String.valueOf(result1);
                        checkPercentTv.setText(currentWeight+"%");
                        progressBar.setProgress(result1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(StockCheckActivity.this,
                            "불러오기 실패", Toast.LENGTH_SHORT).show();
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
                Map<String, String> param = new HashMap<>();
                param.put("stock_seq", String.valueOf(stock_seq));
                return param;
            }
        };

        queue.add(request);
    }


}