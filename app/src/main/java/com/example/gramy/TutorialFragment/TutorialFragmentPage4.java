package com.example.gramy.TutorialFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gramy.HomeActivity;
import com.example.gramy.Join_Login.LoginActivity;
import com.example.gramy.R;

public class TutorialFragmentPage4 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tutorial_page4, container, false);

        Button btnTutorialEnd = view.findViewById(R.id.btnTutorialEnd);
        btnTutorialEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}