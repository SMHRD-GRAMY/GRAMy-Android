package com.example.gramy.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.R;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ItemViewHolder>{

    private Context context;
    private ArrayList<SettingData> Setting_Data = new ArrayList<>();

    public SettingAdapter(Context context, ArrayList<SettingData> Setting_Data){
        this.context = context;
        this.Setting_Data = Setting_Data;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settinglist_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(Setting_Data.get(position));
    }

    @Override
    public int getItemCount() {
        return Setting_Data.size();
    }

    void addItem(SettingData data){
        Setting_Data.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSettingItem;
        private ImageView imgSettingItem;

        ItemViewHolder(View itemView){
            super(itemView);

            tvSettingItem = itemView.findViewById(R.id.tvSettingItem);
            imgSettingItem = itemView.findViewById(R.id.imgSettingItem);
        }

        void onBind(SettingData data){
            tvSettingItem.setText(data.getMenu());
            imgSettingItem.setImageResource(data.getImgId());
        }
    }
}
