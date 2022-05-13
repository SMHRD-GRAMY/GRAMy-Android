package com.example.gramy.setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gramy.R;

public class AddStoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_store, container, false);

        Button btnBack = rootView.findViewById(R.id.btnBack);
        Button btnModifyStore = rootView.findViewById(R.id.btnModifyStore);
        Button btnEnrollStore = rootView.findViewById(R.id.btnEnrollStore);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragSettingmain fragSettingmain = new fragSettingmain();
                transaction.replace(R.id.constraint, fragSettingmain);
                transaction.commit();
            }
        });
        return rootView;
    }
}