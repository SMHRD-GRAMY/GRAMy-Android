package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.gramy.Join_Login.LoginActivity;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 800);

    }
}
