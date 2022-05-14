package com.example.gramy.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gramy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fragSettingmain extends Fragment {

    private RecyclerView rcSettingItem;
    private SettingAdapter adapter;
    private ArrayList<SettingData> list = new ArrayList<>();
    RecyclerView.Adapter SettingAdapter;

    int j = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_frag_settingmain, container, false);

        rcSettingItem = rootView.findViewById(R.id.rcSettingItem);
        rcSettingItem.setHasFixedSize(true);
        adapter = new SettingAdapter(getActivity(), list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcSettingItem.setLayoutManager(layoutManager);
        rcSettingItem.setItemAnimator(new DefaultItemAnimator());
        rcSettingItem.setAdapter(adapter);

        getData(rootView);
        j = 1;
        return rootView;

    }

    private void getData(ViewGroup rootView) {
        List<String> menu = Arrays.asList("공지사항","버전정보", "GRAMy소식","가게추가", "알림수신동의", "실험실", "고객센터", "로그아웃","개인정보수정");
        List<Integer> imgId = Arrays.asList(
                R.drawable.set_info,
                R.drawable.set_version,
                R.drawable.set_gramy,
                R.drawable.set_modify,
                R.drawable.set_insert,
                R.drawable.set_agree,
                R.drawable.set_test,
                R.drawable.set_logout,
                R.drawable.set_contact
        );

        for (int i=0; i<menu.size(); i++){
            SettingData data = new SettingData();
            data.setMenu(menu.get(i));
            data.setImgId(imgId.get(i));
            if(j == 0){
                adapter.addItem(data);
            }

        }
        adapter.notifyDataSetChanged();
    }
}