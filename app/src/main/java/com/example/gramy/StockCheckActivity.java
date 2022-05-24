package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.gramy.Vo_Info.StockDetailVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockCheckActivity extends AppCompatActivity {

    ArrayList<StockDetailVO> items = new ArrayList<StockDetailVO>();
    RequestQueue queue;
    Button updateCheckBtn,updateCancelBtn;
    ProgressBar progressBar;
    TextView checkWeightTv,checkNameTv,checkRegisterTv,checkShelfLifeTv,checkOrderTv;
    TextView checkPercentTv;
    int firstWeight,secondWeight;
    int stock_seq;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check);

        //초기화
        progressBar = findViewById(R.id.progressBar2); // 프로그래스바
        checkWeightTv = findViewById(R.id.checkWeightTv);// 현재 무게
        checkNameTv= findViewById(R.id.checkNameTv);
        checkRegisterTv= findViewById(R.id.checkRegisterTv);
        checkShelfLifeTv= findViewById(R.id.checkSelfLifeTv);
        checkOrderTv= findViewById(R.id.checkOrderTv);
        checkPercentTv =  findViewById(R.id.checkPercentTv); //퍼센트뷰
        updateCheckBtn = findViewById(R.id.checkModifyBtn);
        updateCancelBtn=findViewById(R.id.checkCancelBtn);


        //큐초기화
        queue = Volley.newRequestQueue(StockCheckActivity.this);
        //인텐트를 통한 선반 번호 가져오기
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        System.out.println(id);
        stock_seq=intent.getIntExtra("stock_seq",0);
        //선반의 디테일 내용 가져오기
        getDetail(stock_seq);

        updateCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StockModifyActivity.class);

                StockDetailVO StockDetailVO=items.get(0);
                System.out.println(StockDetailVO);
                //선반 번호 물품 이름 무게 유통기한 보내기
                intent.putExtra("id",id);
                intent.putExtra("stock_seq",items.get(0).getStock_seq());
                intent.putExtra("stock_name",items.get(0).getStock_name());
                intent.putExtra("stock_weight",items.get(0).getStock_weight());
                intent.putExtra("stock_shelfLife",items.get(0).getStock_shelfLife());
                intent.putExtra("stock_order",items.get(0).getStock_order());
                startActivity(intent);


            }
        });
        updateCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        });

    }



    // 목록 가져오는 메서드
    private void getDetail(int stock_seq) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/product/detailItem";
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
                        String get_weight_value = jsonObject.getString("weight_value");
                        int weight_value=0;
                        if(get_weight_value.equals("null")){
                            weight_value=0;
                        }else{
                            weight_value=Integer.parseInt(get_weight_value);
                        }
                        String weight_date=jsonObject.getString("weight_date");
                        StockDetailVO item = new StockDetailVO(stock_seq,stock_name,stock_weight,stock_date,stock_shelfLife,shelf_seq,stock_order,weight_value,weight_date);
                        items.add(item);


                        String [] strArr = items.get(0).getStock_date().substring(0,11).split("-");
                        String parsedStr = strArr[0]+"년 " + strArr[1].substring(1,2)+"월 " + strArr[2].substring(0,2)+"일";

                        checkNameTv.setText(items.get(0).getStock_name()); // 재고명
                        checkWeightTv.setText(items.get(0).getWeight_value()+"/"+items.get(0).getStock_weight()); // 무게
                        checkRegisterTv.setText(parsedStr); // 등록날짜
                        checkShelfLifeTv.setText(items.get(0).getStock_shelfLife()); // 유통기한
                        checkOrderTv.setText(items.get(0).getStock_order()); // 주문방법


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