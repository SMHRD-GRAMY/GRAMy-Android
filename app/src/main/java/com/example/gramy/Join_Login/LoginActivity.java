package com.example.gramy.Join_Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.gramy.StockActivity;
import com.example.gramy.TutorialActivity;
import com.example.gramy.Vo_Info.GramyUserVO;
import com.example.gramy.Vo_Info.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonParser;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {


    RequestQueue queue;
    StringRequest request;

    private NaverUserVO model;
    private String user_id = "";
    private String user_pw = "";
    private String user_phone = "";
    private String user_addr = "";
    private String user_role = "";
    private String user_joindate = "";
    private String user_name = "";
    private String user_gender = "";

    // 네이버로그인
    private static String OAUTH_CLIENT_ID = "ww2RvyEq7Kl37_O2ZoMo";
    private static String OAUTH_CLIENT_SECRET = "NB7Iv8B37s";
    private static String OAUTH_CLIENT_NAME = "Gramy";
    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;
    private static OAuthLoginButton btnNaverLogin;

    private static final String TAG = "LoginActivity";

    private Button btnLogin, btnFindId, btnFindPw, btnGoJoin;
    private ImageButton btnKakaoLogin;
    private EditText edtId, edtPw;
    TextView tvGoJoin;

    // 페이스북
    private static LoginButton btnFacebookLogin;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        edtId = findViewById(R.id.edtId);
        edtPw = findViewById(R.id.edtPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoJoin = findViewById(R.id.btnGoJoin);
        btnKakaoLogin = findViewById(R.id.btnKakaoLogin);
        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
        btnFindId = findViewById(R.id.btnFindId);
        btnFindPw = findViewById(R.id.btnFindPw);
        tvGoJoin = findViewById(R.id.tvGoJoin);

        tvGoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        mContext = this;
        initData();

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
        // 아이디 찾기 버튼
        btnFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 버튼
        btnFindPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindPwActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int method = Request.Method.POST;
                String server_url = "http://172.30.1.52:8082/androidlogin.do";

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
                                        setLoginInfo(user_id, user_phone, user_addr, user_role, user_joindate, user_name, user_gender);
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

        // 페이스북로그인
        btnFacebookLogin.setPermissions(Arrays.asList(EMAIL,USER_POSTS));
        btnFacebookLogin.setAuthType(AUTH_TYPE);

        btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setResult(RESULT_OK);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
            }

            @Override
            public void onError(@NonNull FacebookException e) {
            }
        });

        // 카카오로그인
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
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

    public void setLoginInfo(String user_id, String user_phone, String user_addr, String user_role, String user_joindate, String user_name, String user_gender){
        SharedPreferences sf_login = getSharedPreferences("sf_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf_login.edit();
        editor.putBoolean("check_login", true);
        editor.putString("user_id", user_id);
        editor.putString("user_phone", user_phone);
        editor.putString("user_addr", user_addr);
        editor.putString("user_role", user_role);
        editor.putString("user_joindate", user_joindate);
        editor.putString("user_name", user_name);
        editor.putString("user_gender", user_gender);
        editor.apply();
    }

    public void snsLogin(String user_id, String user_phone, String user_addr, String user_role, String user_joindate, String user_name, String user_gender){
        SharedPreferences sf_login = getSharedPreferences("sf_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf_login.edit();
        editor.putString("user_id", user_id);
        editor.putString("user_phone", user_phone);
        editor.putString("user_addr", user_addr);
        editor.putString("user_role", user_role);
        editor.putString("user_joindate", user_joindate);
        editor.putString("user_name", user_name);
        editor.putString("user_gender", user_gender);
        editor.apply();
    }

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        btnNaverLogin = findViewById(R.id.btnNaverLogin);
        btnNaverLogin.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    // 네이버 로그인
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if(success){
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                getUser(accessToken);
            }else{
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Log.d(TAG, "errorCode : " + errorCode);
                Log.d(TAG, "errorDesc : " + errorDesc);
            }
        }
    };

    private class GetUserTask extends ThreadTask<String, String>{
        @Override
        protected String doInBackground(String s) {
            String header = "Bearer " + s;
            String url = "https://openapi.naver.com/v1/nid/me";

            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("Authorization", header);
            String responseBody = get(url, requestHeaders);

            return responseBody;
        }

        private String get(String url, Map<String, String> requestHeaders){
            HttpURLConnection connection = connect(url);
            try {
                connection.setRequestMethod("GET");
                for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return readBody(connection.getInputStream());
                } else {
                    return readBody(connection.getErrorStream());
                }
            } catch (IOException e) {
                throw new RuntimeException("API 요청 및 응답 실패");
            } finally {
                connection.disconnect();
            }
        }

        private HttpURLConnection connect(String apiurl){
            try{
                URL url = new URL(apiurl);
                return (HttpURLConnection)url.openConnection();
            } catch (MalformedURLException e) {
                throw new RuntimeException("API URL이 잘못되었습니다. : " + apiurl, e);
            } catch (IOException e) {
                throw new RuntimeException("연결을 실패했습니다. : " + apiurl, e);
            }
        }

        private String readBody(InputStream body){
            InputStreamReader streamReader = new InputStreamReader(body);
            try(BufferedReader lineReader = new BufferedReader(streamReader)){
                StringBuilder responseBody = new StringBuilder();
                String line;
                while((line = lineReader.readLine()) != null){
                    responseBody.append(line);
                }
                return responseBody.toString();
            } catch (IOException e) {
                throw new RuntimeException("API 응답을 읽는데 실패했습니다. ", e);
            }
        }

        /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);*/

        /*
        nickname, email, gender, birthday 외에도 회원 이름(본명?), 프로필 사진, 연령대도 가져올 수 있음.
         */
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.getString("resultcode").equals("00")) {
                    JSONObject object = new JSONObject(jsonObject.getString("response"));
                    String name = object.getString("name");
                    String email = object.getString("email");
                    model = new NaverUserVO(name, email);
                    Log.v("네이버", "유저 정보 : " + model);
                    snsLogin(user_id, "", "", "", "", name, "");
                }
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("Data", model);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void redirectSignupActivity(){
        final Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    // 페이스북 해쉬키 가져오기
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    // 페이스북
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getUser(String token){
        new GetUserTask().execute(token);
    }

    // 네이버 로그아웃
    private class RemoveTokenTask extends ThreadTask<String, Void> {

        @Override
        protected Void doInBackground(String arg) {
            boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);

            if(!isSuccessDeleteToken){
                Log.d("REMOVE", "errorCode : " + mOAuthLoginInstance.getLastErrorCode(mContext));
                Log.d("REMOVE", "errorDesc : " + mOAuthLoginInstance.getLastErrorDesc(mContext));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Result) {
            Toast.makeText(mContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
