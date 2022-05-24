package com.example.gramy;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class StockActivity<sPickDate> extends Activity {

    int shelf_seq;
    EditText stockNameEdt,stockWeightEdt,stockOrderEdt;
    Button btnenroll1,getWeightBtn;
    RequestQueue queue;
    StringRequest request;
    //View
    private TextView sText;
    private ImageButton ddPickDate; // dd : s
    //년,월,일,시,분
    private int mYear,mMonth,mDay,mYear1,mMonth1,mDay1;
    //Dialog
    static final int DATE_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID1 = 1;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        //초기화
        getWeightBtn=findViewById(R.id.getWeightBtn);
        btnenroll1 = findViewById(R.id.checkModifyBtn);
        stockNameEdt=findViewById(R.id.stockNameEdt);
        stockWeightEdt=findViewById(R.id.stockWeightEdt);
        stockOrderEdt=findViewById(R.id.stockNameEdt);


        //View 참조
        sText = (TextView)findViewById(R.id.stockShelfLifeEdt);
        ddPickDate = (ImageButton)findViewById(R.id.ddPickDate);
        ddPickDate.bringToFront();

        //시간설정 이벤트
        ddPickDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID1);
            }
        });

        //현재 날짜,시간 가져오기
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        //텍스트뷰 갱신
        updateDisplay();


        queue = Volley.newRequestQueue(StockActivity.this);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        shelf_seq=intent.getIntExtra("shelf_seq",0);
        System.out.println(shelf_seq);

        //무게 가져오는 버튼
        getWeightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(id);
                getWeight(id);
            }
        });

        //등록 및 위치 변경 버튼
        btnenroll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String shelf_seq = String.valueOf(intent.getIntExtra("shelf_seq", 0));
                System.out.println(shelf_seq);
                String name=stockNameEdt.getText().toString();
                String weight=String.valueOf(stockWeightEdt.getText());
                String shelfLife=sText.getText().toString();
                String order=stockOrderEdt.getText().toString();

                insertStock(shelf_seq,name,weight,shelfLife,order);
                Toast.makeText(StockActivity.this,
                        "요청성공!",
                        Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(StockActivity.this, HomeActivity.class);
                startActivity(intent1);
                finish();

    }

    //volly로 요청하기
    public void insertStock(String shelf_seq,String name,String weight,String shelfLife,String order){
        int method = Request.Method.POST;
        String server_url = "http://121.147.52.210:8082/product/insertstock";
        request = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "물품이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StockActivity.this,
                                "요청실패>>" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> param = new HashMap<>();
                param.put("shelf_seq",shelf_seq);
                param.put("stock_name",name);
                param.put("stock_weight",weight);
                param.put("stock_shelfLife",shelfLife);
                param.put("stock_order",order);

                return param;
            }
        };
        queue.add(request);
    }
        });


    }



    //텍스트뷰 갱신
    private void updateDisplay(){
        sText.setText(String.format("%d년 %d월 %d일", mYear1, mMonth1+1, mDay1));
    }

    // 무게 가져오는 메서드
    private void getWeight(int id){
        int method = Request.Method.GET;
        String server_url = "http://172.30.1.40:8083/getweight/"+id;// 하드웨어 url
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String setWeight=response;
                stockWeightEdt.setText(setWeight);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    //DatePicker 리스너
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    //DatePicker 리스너
    private DatePickerDialog.OnDateSetListener mDateSetListener1 =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear1 = year;
                    mMonth1 = monthOfYear;
                    mDay1 = dayOfMonth;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID :
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case DATE_DIALOG_ID1 :
                return new DatePickerDialog(this, mDateSetListener1, mYear1, mMonth1, mDay1);
        }

        return null;
    }


}

