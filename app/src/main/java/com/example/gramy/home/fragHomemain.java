package com.example.gramy.home;

import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gramy.GoBoardActivity;
import com.example.gramy.GoMgShelfActivity;
import com.example.gramy.GoPdCheck;
import com.example.gramy.R;
import com.example.gramy.ReportActivity;


public class fragHomemain extends Fragment {
    // 화면 설계 후 버튼 누르면 화면이동!
    Intent intent = new Intent();

    TextView tvShelfTitle;
    Button btnShelfRegister, btnStock1, btnStock2, btnStock3, btnStock4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_homemain, container, false);

        tvShelfTitle = view.findViewById(R.id.tvShelfTitle);
        btnShelfRegister = view.findViewById(R.id.btnShelfRegister);
        btnStock1 = view.findViewById(R.id.btnStock1);
        btnStock2 = view.findViewById(R.id.btnStock2);
        btnStock3 = view.findViewById(R.id.btnStock3);
        btnStock4 = view.findViewById(R.id.btnStock4);

        return view;
    }
}