package com.example.gramy.Join_Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.HomeActivity;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.GramyUserVO;
import com.example.gramy.Vo_Info.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FindIdActivity extends AppCompatActivity {

    EditText edtFindIdName, edtFindIdPhone;
    Button btnSearchId, btnGoLogin;
    TextView tvFindId;

    private String user_id = "";
    private String user_pw = "";
    private String user_phone = "";
    private String user_addr = "";
    private String user_role = "";
    private String user_joindate = "";
    private String user_name = "";
    private String user_gender = "";

    RequestQueue queue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.gramy.R.layout.activity_find_id);

        edtFindIdName = findViewById(R.id.edtFindidName);
        edtFindIdPhone = findViewById(R.id.edtFindIdPhone);
        btnSearchId = findViewById(R.id.btnSearchId);
        btnGoLogin = findViewById(R.id.btnGoLogin);
        tvFindId = findViewById(R.id.tvFindId);

        queue = Volley.newRequestQueue(FindIdActivity.this);

        btnSearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int method = Request.Method.POST;
                String server_url = "http://119.200.31.65:8082/findid.do";

                request = new StringRequest(
                        method,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.length() > 1) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        user_id = jsonObject.getString("user_id");
                                        user_pw = jsonObject.getString("user_pw");
                                        user_phone = jsonObject.getString("user_phone");
                                        user_addr = jsonObject.getString("user_addr");
                                        user_role = jsonObject.getString("user_role");
                                        user_joindate = jsonObject.getString("user_joindate");
                                        user_name = jsonObject.getString("user_name");
                                        user_gender = jsonObject.getString("user_gender");

                                        GramyUserVO vo = new GramyUserVO(user_id, user_pw, user_name, user_phone, user_addr, user_role, user_joindate, user_gender);
                                        UserInfo.info = vo;

                                        tvFindId.setText("아이디는 "+user_id+" 입니다.");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(FindIdActivity.this,
                                            "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FindIdActivity.this,
                                        "Login Fail" + error.toString(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        param.put("user_name", edtFindIdName.getText().toString());
                        param.put("user_phone", edtFindIdPhone.getText().toString());
                        return param;
                    }
                };

                request.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 50000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 50000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });
                queue.add(request);

            }
        });

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindIdActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}