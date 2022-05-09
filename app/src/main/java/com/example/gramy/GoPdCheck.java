package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gramy.stock.stockActivity;

public class GoPdCheck extends AppCompatActivity {

    Button btnChange;
    ImageButton imgScale1, imgScale2, imgScale3, imgScale4;
    TextView tvThing1;
    String unkown = "미등록";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_ck_pd);

        btnChange = findViewById(R.id.btnChange);

        //등록 및 위치 변경 버튼
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoPdCheck.this, GoMgShelfActivity.class);
                startActivity(intent);
            }
        });
        //  선반이미지 클릭시
        ImageButton imgScale1 = (ImageButton) findViewById(R.id.imgScale1);
        imgScale1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if (v.getId() == R.id.imgScale1) {
                    if (v.getId() == R.id.tvThing1) {
                        Intent intent = new Intent(GoPdCheck.this, stockActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(GoPdCheck.this, GoBoardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }

                }
            }

        });
    }
}