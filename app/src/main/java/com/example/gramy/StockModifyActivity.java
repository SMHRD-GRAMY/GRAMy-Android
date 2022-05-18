package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.gramy.Vo_Info.StockDetailVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StockModifyActivity extends AppCompatActivity {
    EditText updateNameEdt,updateWeightEdt,updateShelfLifeEdt,updateOrderEdt;
    Button checkCancelBtn,checkModifyBtn;
    RequestQueue queue;
    int stock_seq;
    String stock_name;
    int stock_weight;
    String stock_shelfLife;
    String stock_order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_modify);

        updateNameEdt=findViewById(R.id.updateNameEdt);
        updateWeightEdt=findViewById(R.id.updateWeightEdt);
        updateShelfLifeEdt=findViewById(R.id.updateShelfLifeEdt);
        updateOrderEdt=findViewById(R.id.updateOrderEdt);
        checkCancelBtn=findViewById(R.id.checkCancelBtn);
        checkModifyBtn=findViewById(R.id.checkModifyBtn);

        //큐초기화
        queue = Volley.newRequestQueue(StockModifyActivity.this);
        //인텐트를 통한 선반 번호 가져오기
        Intent intent=getIntent();
        stock_seq=intent.getIntExtra("stock_seq",0);
        System.out.println(stock_seq);
        stock_name=intent.getStringExtra("stock_name");
        stock_weight=intent.getIntExtra("stock_weight",0);
        stock_shelfLife=intent.getStringExtra("stock_shelfLife");
        stock_order=intent.getStringExtra("stock_order");

        // 물품의 정보 가져오기
        updateNameEdt.setText(stock_name);
        updateWeightEdt.setText(String.valueOf(stock_weight));
        updateShelfLifeEdt.setText(stock_shelfLife);
        updateOrderEdt.setText(stock_order);

        checkModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(stock_seq);
                stock_name=updateNameEdt.getText().toString();
                stock_weight=Integer.parseInt(updateWeightEdt.getText().toString());
                stock_shelfLife=updateShelfLifeEdt.getText().toString();
                stock_order=updateOrderEdt.getText().toString();

                updateStock(stock_seq,stock_name,stock_weight,stock_shelfLife,stock_order);




            }
        });


    }

    // 목록 가져오는 메서드
    private void updateStock(int stock_seq,String stock_name,int stock_weight, String stock_shelfLife,String stock_order) {
        int method = Request.Method.POST;
        String server_url = "http://172.30.1.42:8082/product/insertweight";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "성공적으로 업데이트 되었습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), GoPdCheck.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockModifyActivity.this, "실패했습니다", Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // 보낼 데이터
                Map<String, String> param = new HashMap<>();
                param.put("stock_seq", String.valueOf(stock_seq));
                param.put("stock_name", stock_name);
                param.put("stock_weight",String.valueOf(stock_weight));
                param.put("stock_shelfLife",stock_shelfLife);
                param.put("stock_order",stock_order);
                return param;
            }
        };

        queue.add(request);
    }

}