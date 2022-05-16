package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gramy.setting.fragSettingmain;

public class BetaServiceActivity extends AppCompatActivity {

    ImageButton btnBack;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_beta_service);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(view -> {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);

            transaction.replace(R.layout.fragment_frag_settingmain, fragSettingmain.class, null);

            transaction.commit();
        });
    }
}