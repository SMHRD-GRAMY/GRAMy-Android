package com.example.gramy;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gramy.home.fragHomemain;
import com.example.gramy.news.fragNewsmain;
import com.example.gramy.setting.fragModify;
import com.example.gramy.setting.fragSettingmain;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {

    public static Context context_home;
    private long backpressedTime = 0;

    BottomNavigationView bottomNavi;
    fragHomemain fragHomemain;
    fragNewsmain fragNewsmain;
    fragSettingmain fragSettingmain;
    fragModify fragModify;

    Button btnGoPdCheck, btnGoReport, btnGoBoard, btnGoMgShelf;
    public TextView tvTitleGramy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context_home = this;

        bottomNavi = findViewById(R.id.bottomNavi);
        fragHomemain = new fragHomemain();
        fragNewsmain = new fragNewsmain();
        fragSettingmain = new fragSettingmain();
        tvTitleGramy = findViewById(R.id.tvTitleGramy);
        fragModify = new fragModify();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).addToBackStack(null).commit();




        /*if(fragModify.getContext().equals("context_modify")){
            Log.v("프래그먼트 값 : ", ">>"+ getSupportFragmentManager().beginTransaction().replace(R.id.container, fragHomemain).addToBackStack(null));
        }*/
        /*if(액티비티가 modify액티비티 일때만 ){
            tvTitleGramy.setText("개인정보수정");
        }else{
            tvTitleGramy.setText("Gramy");
        }*/

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
