package com.example.gramy;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gramy.home.fragHomemain;
import com.example.gramy.news.fragNewsmain;
import com.example.gramy.setting.fragSettingmain;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {

    private long backpressedTime = 0;
    BottomNavigationView bottomNavi;
    TextView tvTitleGramy, tvAddStore, tvAlarm, tvBetaService;
    ImageButton btnBack;

    fragHomemain fragHomemain;
    fragNewsmain fragNewsmain;
    fragSettingmain fragSettingmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvTitleGramy = findViewById(R.id.tvTitleGramy);
        tvAddStore = findViewById(R.id.tvAddStore);
        tvAlarm = findViewById(R.id.tvAlarm);
        tvBetaService = findViewById(R.id.tvBetaService);
        btnBack = findViewById(R.id.btnBack);

        bottomNavi = findViewById(R.id.bottomNavi);
        fragHomemain = new fragHomemain();
        fragNewsmain = new fragNewsmain();
        fragSettingmain = new fragSettingmain();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).addToBackStack(null).commit();

        bottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).addToBackStack(null).commit();
                } else if (itemId == R.id.news) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragNewsmain).addToBackStack(null).commit();
                } else if (itemId == R.id.setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragSettingmain).addToBackStack(null).commit();
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
