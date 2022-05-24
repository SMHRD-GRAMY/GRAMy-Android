package com.example.gramy.Join_Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.R;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    EditText edtJoinId, edtJoinPw, edtJoinPwCheck, edtJoinName, edtJoinPhone, edtJoinAddr;
    Button btnJoinIdCheck, btnJoin;
    RadioGroup rgGender;
    RadioButton radioMan, radioWoman, radioNotting;

    RequestQueue queue;
    StringRequest request;

    String chbResult = "";
    String id,pw,pwCheck,name,phone,addr,gender;
    boolean radio_group_your_choice = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edtJoinId = findViewById(R.id.edtJoinId);
        edtJoinPw = findViewById(R.id.edtJoinPw);
        edtJoinPwCheck = findViewById(R.id.edtJoinPwCheck);
        edtJoinName = findViewById(R.id.edtJoinName);
        edtJoinPhone = findViewById(R.id.edtJoinPhone);
        edtJoinAddr = findViewById(R.id.edtJoinAddr);
        btnJoinIdCheck = findViewById(R.id.btnJoinIdCheck);
        btnJoin = findViewById(R.id.btnJoin);
        rgGender = findViewById(R.id.rgGender);
        radioMan = findViewById(R.id.radioMan);
        radioWoman = findViewById(R.id.radioWoman);
        radioNotting = findViewById(R.id.radioNotting);
        queue = Volley.newRequestQueue(JoinActivity.this);





        if (radio_group_your_choice != true) {
            rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.ModifyradioMan) {
                        chbResult = "man";
                    } else if (i == R.id.ModifyradioWoman) {
                        chbResult = "woman";
                    } else if (i == R.id.ModifyradioNotting) {
                        chbResult = "notting";
                    }
                }
            });
        }
        btnJoinIdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtJoinId = findViewById(R.id.edtJoinId);
                idcheck(id);


            }
        });




        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioMan) {
                    chbResult = "man";
                } else if (i == R.id.radioWoman) {
                    chbResult = "woman";
                } else if (i == R.id.radioNotting) {
                    chbResult = "notting";
                }
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = edtJoinId.getText().toString();
                pw = edtJoinId.getText().toString();
                pwCheck = edtJoinId.getText().toString();
                name = edtJoinId.getText().toString();
                phone = edtJoinId.getText().toString();
                addr = edtJoinId.getText().toString();
                gender = edtJoinId.getText().toString();
                System.out.println(id);
                Log.v("성별", "값 : " + chbResult);
                Log.v("성별", "값 : " + id);
                joinUser(id,pw,name,phone,addr,chbResult);

            }
        });




    }

    //volly로 아이디중복체크
    private void idCheck2(String id){
        Log.v("성별", "값 : " + chbResult);
        System.out.println(id);

        int method = Request.Method.POST;
        String server_url = "http://172.30.1.52:8082/userIdCk.do";

        request = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(JoinActivity.this,
                                "사용가능한아이디입니다",
                                Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JoinActivity.this,
                                "요청실패>>" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", id);
                return param;
            }
        };
        queue.add(request);
    }

    //volly로 가입가능한 아이디 여부확인하기
    public void idcheck(String id){
        System.out.println(id);
        int method = Request.Method.POST;
        String server_url = "http://119.200.31.80:8082/androidjoin.do";

        request = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(JoinActivity.this,
                                "요청성공!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JoinActivity.this,
                                "요청실패>>" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", id);
                param.put("user_pw", pw);
                param.put("user_name", name);
                param.put("user_phone",phone);
                param.put("user_addr", addr);
                param.put("user_gender", gender);
                return param;
            }
        };
        queue.add(request);
    }
    //volly로 회원가입 디비에 보내기
    public void joinUser(String id,String pw,String name,String phone,String addr,String gender){
        Log.v("성별", "값 : " + chbResult);
        System.out.println(id);

        int method = Request.Method.POST;
        String server_url = "http://121.147.52.210:8082/androidjoin.do";

        request = new StringRequest(
                method,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(JoinActivity.this,
                                "요청성공!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JoinActivity.this,
                                "요청실패>>" + error.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", id);
                param.put("user_pw", pw);
                param.put("user_name", name);
                param.put("user_phone",phone);
                param.put("user_addr", addr);
                param.put("user_gender", gender);
                return param;
            }
        };
        queue.add(request);
    }
}