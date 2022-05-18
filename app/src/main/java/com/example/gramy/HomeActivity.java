package com.example.gramy;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.gramy.Join_Login.NaverUserVO;
import com.example.gramy.home.fragHomemain;
import com.example.gramy.news.fragNewsmain;
import com.example.gramy.setting.fragSettingmain;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kakao.auth.Session;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;


public class HomeActivity extends AppCompatActivity {

    public static Context context_home;
    public static HomeActivity btnBackVis;
    private long backpressedTime = 0;
    BottomNavigationView bottomNavi;
    public TextView tvTitleGramy;

    public ImageButton btnBack;

    fragHomemain fragHomemain;
    fragNewsmain fragNewsmain;
    fragSettingmain fragSettingmain;

    public void replace(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context_home = this;
        btnBackVis = this;

        tvTitleGramy = findViewById(R.id.tvTitleGramy);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setVisibility(View.GONE);

        bottomNavi = findViewById(R.id.bottomNavi);
        fragHomemain = new fragHomemain();
        fragNewsmain = new fragNewsmain();
        fragSettingmain = new fragSettingmain();

        Intent intent = getIntent();
        if(intent != null){
            NaverUserVO model;
            model = (NaverUserVO) intent.getSerializableExtra("Data");
            Log.v("모델","값 : "+model);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replace(fragSettingmain);
                btnBack.setVisibility(View.GONE);
                tvTitleGramy.setText("GRAMy");
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).addToBackStack(null).commit();

        bottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).addToBackStack(null).commit();
                    tvTitleGramy.setText("GRAMy");
                    btnBack.setVisibility(View.GONE);
                } else if (itemId == R.id.news) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragNewsmain).addToBackStack(null).commit();
                    tvTitleGramy.setText("GRAMy");
                    btnBack.setVisibility(View.GONE);
                } else if (itemId == R.id.setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragSettingmain).addToBackStack(null).commit();
                    tvTitleGramy.setText("GRAMy");
                    btnBack.setVisibility(View.GONE);
                }
                return true;
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).addToBackStack(null).commit();
                    bottomNavi.getMenu().getItem(0).setChecked(true);
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
