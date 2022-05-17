package com.example.gramy.Join_Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.gramy.R;
import com.example.gramy.Vo_Info.GramyUserVO;
import com.example.gramy.Vo_Info.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FindPwActivity extends AppCompatActivity {

    TextView tvTitleModyPw, tvPwModify, tvRePwModify;
    EditText edtFindPwId, edtFindPwName, edtFindPwPhone, edtPwModify, edtRePwModify;
    Button btnFindPw2, btnGoLoginPw, btnReModify;

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
        setContentView(R.layout.activity_find_pw);

        tvTitleModyPw = findViewById(R.id.tvTitleModyPw);
        tvPwModify = findViewById(R.id.tvPwModify);
        tvRePwModify = findViewById(R.id.tvRePwModify);
        edtFindPwId = findViewById(R.id.edtFindPwId);
        edtFindPwPhone = findViewById(R.id.edtFindPwPhone);
        edtFindPwName = findViewById(R.id.edtFindPwName);
        edtPwModify = findViewById(R.id.edtPwModify);
        edtRePwModify = findViewById(R.id.edtRePwModify);
        btnFindPw2 = findViewById(R.id.btnFindPw2);
        btnGoLoginPw = findViewById(R.id.btnGoLoginPw);
        btnReModify = findViewById(R.id.btnReModify);

        queue = Volley.newRequestQueue(FindPwActivity.this);

        btnReModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int method = Request.Method.POST;
                String server_url = "http://119.200.31.65:8082/modifypw.do";

                request = new StringRequest(
                        method,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                AlertDialog.Builder alter = new AlertDialog.Builder(FindPwActivity.this);
                                alter.setTitle("비밀번호 수정");
                                alter.setMessage("비밀번호를 수정하시겠습니까?");

                                alter.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(FindPwActivity.this, "수정되었습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                alter.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alter.show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FindPwActivity.this,
                                        "Login Fail" + error.toString(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        param.put("user_id", edtFindPwId.getText().toString());
                        param.put("user_pw", edtPwModify.getText().toString());
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

        btnFindPw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int method = Request.Method.POST;
                String server_url = "http://119.200.31.65:8082/findpw.do";

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

                                        showAlertDialog();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(FindPwActivity.this,
                                            "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FindPwActivity.this,
                                        "Login Fail" + error.toString(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        param.put("user_id", edtFindPwId.getText().toString());
                        param.put("user_phone", edtFindPwPhone.getText().toString());
                        param.put("user_name", edtFindPwName.getText().toString());
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

        btnGoLoginPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindPwActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 비밀번호 찾기 성공시 알림창
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
        builder.setTitle("검색한 정보가 존해합니다.");
        builder.setMessage("비밀번호를 수정하시겠습니까?.");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvTitleModyPw.setVisibility(View.VISIBLE);
                tvPwModify.setVisibility(View.VISIBLE);
                tvRePwModify.setVisibility(View.VISIBLE);
                edtPwModify.setVisibility(View.VISIBLE);
                edtRePwModify.setVisibility(View.VISIBLE);
                btnGoLoginPw.setVisibility(View.VISIBLE);
                btnReModify.setVisibility(View.VISIBLE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}