package com.example.gramy.setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gramy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fragSettingmain extends Fragment {

    private RecyclerView rcSettingItem;
    private SettingAdapter adapter;
    private ArrayList<SettingData> list = new ArrayList<>();
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
        List<String> menu = Arrays.asList("테스트1","테스트2","테스트3","테스트4","테스트5");
        List<Integer> imgId = Arrays.asList(
                R.drawable.ic_home,
                R.drawable.ic_setting,
                R.drawable.ic_news,
                R.drawable.ic_user,
                R.drawable.ic_search);

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