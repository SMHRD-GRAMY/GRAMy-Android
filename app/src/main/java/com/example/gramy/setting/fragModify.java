package com.example.gramy.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.gramy.HomeActivity;
import com.example.gramy.R;

import java.util.HashMap;
import java.util.Map;

public class fragModify extends Fragment {

    public Context context_modify;
    EditText edtModifyId, edtModifyPw, edtModifyPwCheck, edtModifyName, edtModifyPhone, edtModifyAddr;
    Button btnModify, btnModifyCancel;
    RadioGroup rgModifyGender;
    RadioButton ModifyradioMan, ModifyradioWoman, ModifyradioNotting;

    RequestQueue queue;
    StringRequest request;
    String chbModifyResult = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("로그인", "저장 : "+getLoginInfo());

        context_modify = getActivity();

        View view = inflater.inflate(R.layout.fragment_modify, container, false);

        edtModifyId = (EditText) view.findViewById(R.id.edtJoinId);
        edtModifyPw = (EditText) view.findViewById(R.id.edtModifyPw);
        edtModifyPwCheck = (EditText) view.findViewById(R.id.edtModifyPwCheck);
        edtModifyName = (EditText) view.findViewById(R.id.edtModifyName);
        edtModifyPhone = (EditText) view.findViewById(R.id.edtModifyPhone);
        edtModifyAddr = (EditText) view.findViewById(R.id.edtModifyAddr);

        btnModify = (Button) view.findViewById(R.id.btnModify);
        btnModifyCancel = (Button) view.findViewById(R.id.btnModifyCancel);

        edtModifyId = (EditText) view.findViewById(R.id.edtJoinId);
        edtModifyId = (EditText) view.findViewById(R.id.edtJoinId);
        edtModifyId = (EditText) view.findViewById(R.id.edtJoinId);

        rgModifyGender = (RadioGroup) view.findViewById(R.id.rgModifyGender);
        ModifyradioMan = (RadioButton) view.findViewById(R.id.ModifyradioMan);
        ModifyradioWoman = (RadioButton) view.findViewById(R.id.ModifyradioWoman);
        ModifyradioNotting = (RadioButton) view.findViewById(R.id.ModifyradioNotting);

        queue = Volley.newRequestQueue(getActivity());

        rgModifyGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.ModifyradioMan){
                    chbModifyResult = "man";
                } else if(i==R.id.ModifyradioWoman){
                    chbModifyResult = "woman";
                } else if(i==R.id.ModifyradioNotting){
                    chbModifyResult = "notting";
                }
            }
        });

        btnModifyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beforePage();
            }
        });


        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("성별", "값 : "+chbModifyResult);

                int method = Request.Method.POST;
                String server_url = "http://119.200.31.65:8082/androidupdate.do";

                request = new StringRequest(
                        method,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){
                                    Toast.makeText(getActivity(),
                                            "요청성공!",
                                            Toast.LENGTH_SHORT).show();

                                    AlertDialog.Builder alter = new AlertDialog.Builder(getContext());
                                    alter.setTitle("개인정보수정");
                                    alter.setMessage("수정이 완료되었습니다.");

                                    alter.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            beforePage();
                                        }
                                    });
                                    alter.show();

                                }else{
                                Toast.makeText(getActivity(),
                                        "수정실패!",
                                        Toast.LENGTH_SHORT).show();
                                }
                                }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(),
                                        "요청실패!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", getLoginInfo());
                        param.put("user_pw", edtModifyPw.getText().toString());
                        Log.v("비밀번호","값: "+ edtModifyPw.getText().toString());
                        param.put("user_name", edtModifyName.getText().toString());
                        param.put("user_phone", edtModifyPhone.getText().toString());
                        param.put("user_addr", edtModifyAddr.getText().toString());
                        param.put("user_gender", chbModifyResult);

                        return param;
                    }
                };
                queue.add(request);
            }
        });
        return  view;
    }

    private String getLoginInfo(){

        SharedPreferences sf_login = getActivity().getSharedPreferences("sf_login", Context.MODE_PRIVATE);
        boolean isLoginChecked = sf_login.getBoolean("check_login", false); //로그인 체크박스의 상태, default : false
        String user_id = sf_login.getString("user_id","");

        return user_id;
    }

    public void beforePage(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragSettingmain fragSettingmain = new fragSettingmain();
        transaction.replace(R.id.container, fragSettingmain);
        transaction.commit();
    }
}