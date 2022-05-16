package com.example.gramy.stock;


import java.util.Calendar;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.gramy.R;

public class StockActivity<sPickDate> extends Activity {

    EditText tvstockName, tvstockweight, tvhwuid, tvstockDate, tvstockshelflife, tvstochOrder;
    Button btnenroll1;


    //View
    private TextView sText;
    private Button sPickDate;
    private TextView mText;
    private Button mPickDate;


    //년,월,일,시,분
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mYear1;
    private int mMonth1;
    private int mDay1;

    //Dialog
    static final int DATE_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID1 = 1;

    RequestQueue queue;
    StringRequest request;





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        btnenroll1 = findViewById(R.id.btnenroll1);

        //등록 및 위치 변경 버튼
        btnenroll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //


                //
                Intent intent = new Intent(StockActivity.this, StockCheckActivity.class);
                startActivity(intent);
            }
        });

        //View 참조
        mText = (TextView)findViewById(R.id.tvstockDate);
        mPickDate = (Button)findViewById(R.id.dPickDate);
        sText = (TextView)findViewById(R.id.tvstockshelflife);
        sPickDate = (Button)findViewById(R.id.ddPickDate);
        //날짜설정 이벤트
        mPickDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        //시간설정 이벤트
        sPickDate.setOnClickListener(new OnClickListener() {
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
    }


    //텍스트뷰 갱신
    private void updateDisplay(){
        mText.setText(String.format("%d년 %d월 %d일", mYear, mMonth+1, mDay));
        sText.setText(String.format("%d년 %d월 %d일", mYear1, mMonth1+1, mDay1));
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

