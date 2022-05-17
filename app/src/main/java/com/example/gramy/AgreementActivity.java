package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.gramy.Join_Login.JoinActivity;

// 이용약관 체크박스 기능구현단입니다.
public class AgreementActivity extends AppCompatActivity {

    // 라디오 버튼 옆에 해당 이용약관 전문 볼 수 있는 용도의 버튼은 만들어놨는데 이 부분은 추후에 기능 구현 진행하겠습니다.
    CheckBox ckAll, ckAgeNo, ckAgeYes, ckGramy, ckPersonalChoose, ckMarketing, ckPersonal;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

        ckAll = findViewById(R.id.ckAll);
        ckAgeNo = findViewById(R.id.ckAgeNo);
        ckAgeYes = findViewById(R.id.ckAgeYes);
        ckGramy = findViewById(R.id.ckGramy);
        ckPersonal = findViewById(R.id.ckPersonal);
        ckPersonalChoose = findViewById(R.id.ckPersonalChoose);
        ckMarketing = findViewById(R.id.ckMarketing);
        btnNext = findViewById(R.id.btnNext);

        ckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckAll.isChecked()) {
                    ckGramy.setChecked(true);
                    ckPersonal.setChecked(true);
                    ckPersonalChoose.setChecked(true);
                    ckMarketing.setChecked(true);
                }else{
                    ckGramy.setChecked(false);
                    ckPersonal.setChecked(false);
                    ckPersonalChoose.setChecked(false);
                    ckMarketing.setChecked(false);
                }
            }
        });
        ckAgeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckAgeNo.isChecked()){
                    ckAgeNo.setChecked(true);
                    ckAgeYes.setChecked(false);
                }else{
                    ckAgeYes.setChecked(false);
                    ckAgeNo.setChecked(true);
                }
            }
        });
        ckAgeYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckAgeYes.isChecked()){
                    if (ckAgeNo.isChecked()){
                        ckAgeNo.setChecked(false);
                        ckAgeYes.setChecked(true);
                    }else{
                        ckAgeYes.setChecked(true);
                        ckAgeNo.setChecked(false);
                    }
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckGramy.isChecked()==true && ckPersonal.isChecked()==true && ckAgeYes.isChecked()==true) {
                    Intent intent = new Intent(AgreementActivity.this, JoinActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AgreementActivity.this, "필수 이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
                    // 나이 체크 안했을 때 Toast 추가 진행하기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
            }
        });

    }
}