package com.example.gramy.stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.gramy.R;

public class StockCheckActivity extends AppCompatActivity {

    private Button button;
    private ProgressBar progressBar;
    private EditText editText;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        editText = (EditText)findViewById(R.id.test1);
        button = (Button)findViewById(R.id.btnenroll1);
    }

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