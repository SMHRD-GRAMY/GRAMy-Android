package com.example.gramy.home;

import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gramy.GoBoardActivity;
import com.example.gramy.GoMgShelfActivity;
import com.example.gramy.GoPdCheck;
import com.example.gramy.GoReportActivity;
import com.example.gramy.R;


public class fragHomemain extends Fragment implements View.OnClickListener{


    // 화면 설계 후 버튼 누르면 화면이동!
    Button btnGoPdCheck, btnGoReport, btnGoBoard, btnGoMgShelf;
    Intent intent = new Intent();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_homemain, container, false);

        btnGoPdCheck = (Button) view.findViewById(R.id.btnGoPdCheck);
        btnGoReport = (Button) view.findViewById(R.id.btnGoReport);
        btnGoBoard = (Button) view.findViewById(R.id.btnGoBoard);
        btnGoMgShelf = (Button) view.findViewById(R.id.btnGoMgShelf);

        btnGoPdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoPdCheck.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        btnGoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoReportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        btnGoBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        btnGoMgShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoMgShelfActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        return view;
    }
}