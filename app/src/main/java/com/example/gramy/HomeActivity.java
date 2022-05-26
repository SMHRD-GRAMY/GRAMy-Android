package com.example.gramy;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gramy.Join_Login.NaverUserVO;
import com.example.gramy.Join_Login.PendingIntentActivity;
import com.example.gramy.Report.fragReportmain;
import com.example.gramy.home.fragHomemain;
import com.example.gramy.Community.fragNewsmain;
import com.example.gramy.setting.fragSettingmain;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {

    public static Context context_home;
    public static HomeActivity btnBackVis;
    private long backpressedTime = 0;
    BottomNavigationView bottomNavi;
    public TextView tvTitleGramy;

    public ImageButton btnBack, btnNoti;

    fragHomemain fragHomemain;
    fragNewsmain fragNewsmain;
    fragSettingmain fragSettingmain;
    fragReportmain fragReportmain;

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

        createNotificationChannel();

        context_home = this;
        btnBackVis = this;

        tvTitleGramy = findViewById(R.id.tvTitleGramy);
        btnBack = findViewById(R.id.btnBack);
        btnNoti = findViewById(R.id.btnNoti);

        btnBack.setVisibility(View.GONE);

        bottomNavi = findViewById(R.id.bottomNavi);
        fragHomemain = new fragHomemain();
        fragNewsmain = new fragNewsmain();
        fragSettingmain = new fragSettingmain();
        fragReportmain = new fragReportmain();

        Intent intent2 = new Intent(HomeActivity.this, PendingIntentActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(HomeActivity.this, 0, intent2, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.alarm_white)
                .setContentTitle("테스트")
                .setContentText("테스트")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);;

        Intent intent = getIntent();
        //intent 로 shelf_seq 가져오기
        int shelf_seq=intent.getIntExtra("shelf_seq",0);
        //번들객체 생성, text값 저장
        Bundle bundle = new Bundle();
        bundle.putInt("shelf_seq",shelf_seq);
        //fragment1로 번들 전달
        fragHomemain.setArguments(bundle);


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

        // 알림
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(HomeActivity.this);
                notificationManager.notify(0, builder.build());
                System.out.println("알람");
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
                } else if(itemId == R.id.report){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragReportmain).addToBackStack(null).commit();
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


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
