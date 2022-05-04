package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    // 로딩화면입니다. 로고만 일단 ImageView로 배치해놨습니다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}