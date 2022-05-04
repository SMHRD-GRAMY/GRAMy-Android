package com.example.gramy.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gramy.R;


public class fragHomemain extends Fragment {

    // 화면 설계 후 버튼 누르면 화면이동!
    Button btnGoPdCheck, btnGoReport, btnGoBoard, btnGoMgShelf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frag_homemain, container, false);

    }
}