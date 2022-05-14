package com.example.gramy;

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

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    EditText edtJoinId, edtJoinPw, edtJoinPwCheck, edtJoinName, edtJoinPhone, edtJoinAddr;
    Button btnIdCheck, btnJoin;
    RadioGroup rgGender;
    RadioButton radioMan, radioWoman, radioNotting;

    RequestQueue queue;
    StringRequest request;
    String chbResult = "";

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
        btnIdCheck = findViewById(R.id.btnIdCheck);
        btnJoin = findViewById(R.id.btnJoin);
        rgGender = findViewById(R.id.rgGender);
        radioMan = findViewById(R.id.radioMan);
        radioWoman = findViewById(R.id.radioWoman);
        radioNotting = findViewById(R.id.radioNotting);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radioMan){
                    chbResult = "man";
                } else if(i==R.id.radioWoman){
                    chbResult = "woman";
                } else if(i==R.id.radioNotting){
                    chbResult = "notting";
                }
            }
        });
        //////////
        queue = Volley.newRequestQueue(JoinActivity.this);

        btnJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.v("성별", "값 : "+chbResult);

                int method = Request.Method.POST;
                String server_url = "http://119.200.31.65:8082/androidjoin.do";

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
                                        "요청실패>>"+error.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", edtJoinId.getText().toString());
                        param.put("user_pw", edtJoinPw.getText().toString());
                        param.put("user_name", edtJoinName.getText().toString());
                        param.put("user_phone", edtJoinPhone.getText().toString());
                        param.put("user_addr", edtJoinAddr.getText().toString());
                        param.put("user_gender", chbResult);
                        return param;
                    }
                };
                queue.add(request);
            }
        });
    }
}