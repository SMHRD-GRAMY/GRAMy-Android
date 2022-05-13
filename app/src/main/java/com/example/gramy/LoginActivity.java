package com.example.gramy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Vo_Info.GramyUserVO;
import com.example.gramy.Vo_Info.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.io.IOException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    EditText edtId, edtPw;
    Button btnLogin, btnGoJoin;

    RequestQueue queue;
    StringRequest request;

    private static final String TAG = "LoginActivity";

    private Button btnLogin, btnFindId, btnFindPw, btnGoJoin;
    private ImageButton btnFacebookLogin, btnKakaoLogin, btnNaverLogin;
    private EditText edtId, edtPw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = findViewById(R.id.edtId);
        edtPw = findViewById(R.id.edtPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoJoin = findViewById(R.id.btnGoJoin);
        btnKakaoLogin = findViewById(R.id.btnKakaoLogin);
        btnNaverLogin = findViewById(R.id.btnNaverLogin);
        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);

        queue = Volley.newRequestQueue(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtId.getText().toString();
                String pw = edtPw.getText().toString();
            }
        });

        btnGoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int method = Request.Method.POST;
                String server_url = "http://119.200.31.65:8082/androidlogin.do";

                request = new StringRequest(
                        method,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.length() > 1) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String user_id = jsonObject.getString("user_id");
                                        String user_pw = jsonObject.getString("user_pw");
                                        String user_phone = jsonObject.getString("user_phone");
                                        String user_addr = jsonObject.getString("user_addr");
                                        String user_role = jsonObject.getString("user_role");
                                        String user_joindate = jsonObject.getString("user_joindate");
                                        String user_name = jsonObject.getString("user_name");
                                        String user_gender = jsonObject.getString("user_gender");

                                        GramyUserVO vo = new GramyUserVO(user_id, user_pw, user_name, user_phone, user_addr, user_role, user_joindate, user_gender);

                                        Log.v("확인 : ", vo.toString());
                                        UserInfo.info = vo;

                                        Intent intent2 = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent2.putExtra("response", response);
                                        startActivity(intent2);
                                        finish();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this,
                                            "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this,
                                        "Login Fail" + error.toString(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        param.put("user_id", edtId.getText().toString());
                        param.put("user_pw", edtPw.getText().toString());
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
        // 카카오로그인
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Log.d("로그인 성공", "로그인 성공");
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if (user != null){
                                Log.d(TAG, "로그인 정보 : "+user);
                }
                if (throwable != null){
                    Log.d("error", throwable.getLocalizedMessage());
                }
                return null;
                }
            });
        }
        updateKakaoLoginUi();
        return null;
    }
            private void updateKakaoLoginUi() {
                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                    @Override
                    public Unit invoke(User user, Throwable throwable) {
                        if (user != null) {
                            Log.d(TAG, "invoke : id " + user.getId());
                        } else {
                            Log.d(TAG, "null");
                        }

                        return null;
                    }
                });
            }
        };
        btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });



    }
}