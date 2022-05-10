package com.example.gramy.setting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ItemViewHolder>{

    private Context context;
    private ArrayList<SettingData> Setting_Data;

    public SettingAdapter(Context context, ArrayList<SettingData> Setting_Data){
        this.context = context;
        this.Setting_Data = Setting_Data;
    }

    // 뷰를 데이터가 연결하는 프로세스를 바인딩이라고 함.
    // viewHolder를 새로 만들어야 할 때마다 메서드 호출. viewHolder와 그에 연결된 view를 생성, 초기화.
    // 뷰의 콘텐츠를 채우지는 않음. 아직 특정 데이터에 바인딩된 상태가 아니기 때문.
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.settinglist_item,parent,false);
        return new ItemViewHolder(view);
    }

    // recyclerView는 viewHolder와 데이터를 연결할 때 메서드 호출.
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position){
        holder.onBind(Setting_Data.get(position));
    }

    @Override
    public int getItemCount() {
        return Setting_Data.size();
    }

    void addItem(SettingData data){
        Setting_Data.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSettingItem;
        private ImageView imgSettingItem;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvSettingItem = itemView.findViewById(R.id.tvSettingItem);
            imgSettingItem = itemView.findViewById(R.id.imgSettingItem);

            tvSettingItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("click", "click");
                }
            });
        }

        void onBind(SettingData data) {
            tvSettingItem.setText(data.getMenu());
            imgSettingItem.setImageResource(data.getImgId());
        }

        public void setItem(SettingData item){
            imgSettingItem.setImageResource(item.getImgId());
            tvSettingItem.setText(item.getMenu());
        }

    }

}
