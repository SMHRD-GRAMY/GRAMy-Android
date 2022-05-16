package com.example.gramy.stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gramy.R;

public class StockCheckActivity extends AppCompatActivity {

    private Button button;
    private ProgressBar progressBar;
    private EditText editText;
    private TextView edtpercent;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2); // 프로그래스바
        editText = (EditText)findViewById(R.id.test11);// 현재 무게
        edtpercent = (TextView) findViewById(R.id.edtpercent); //퍼센트
        button = (Button)findViewById(R.id.btnenroll1);

        String currentWeight=editText.getText().toString();
        edtpercent.setText(currentWeight);

        value = Integer.parseInt(editText.getText().toString());
        if(value<=10){
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
        progressBar.setProgress(value);
    }

    //재고 퍼센트
    public  void onClick(View v) {
        value = Integer.parseInt(editText.getText().toString());
        if(value<=10){
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
        progressBar.setProgress(value);
    }


}