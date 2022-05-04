package com.example.gramy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gramy.home.fragHomemain;
import com.example.gramy.news.fragNewsmain;
import com.example.gramy.setting.fragSettingmain;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavi;
    fragHomemain fragHomemain;
    fragNewsmain fragNewsmain;
    fragSettingmain fragSettingmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavi = findViewById(R.id.bottomNavi);
        fragHomemain = new fragHomemain();
        fragNewsmain = new fragNewsmain();
        fragSettingmain = new fragSettingmain();

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragHomemain).commit();

        bottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId==R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).commit();
                }else if(itemId==R.id.news){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragNewsmain).commit();
                }else if(itemId==R.id.setting){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragSettingmain).commit();
                }

                return true;
            }
        });




    }
}