package com.example.gramy.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gramy.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ItemViewHolder> implements OnListClickListener{

    private Context context;
    private TextView tvSettingItem, tvVersion;
    private ImageView imgSettingItem;
    private Switch switchBtn;
    private ArrayList<SettingData> Setting_Data;

    List<String> alertTitle = Arrays.asList("공지사항","버전정보", "GRAMy소식","가게추가", "알림수신동의", "실험실", "고객센터");
    List<String> alertMessage = Arrays.asList("공지사항","버전정보", "GRAMy소식","가게추가", "알림수신동의", "실험실", "고객센터");
    List<String> alertBtnCheck = Arrays.asList("공지사항","버전정보", "GRAMy소식","가게추가", "알림수신동의", "실험실", "고객센터");

    OnListClickListener listener;

    public SettingAdapter(Context context, ArrayList<SettingData> Setting_Data){
        this.context = context;
        this.Setting_Data = Setting_Data;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.settinglist_item,parent,false);
        return new ItemViewHolder(itemView, this);
    }

    public void setOnItemClickListener(OnListClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ItemViewHolder holder, View view, int position) {
        if (listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.onBind(Setting_Data.get(position));
        if (position != 1) {
            tvVersion.setVisibility(View.GONE);
        }
        if (position != 4) {
            switchBtn.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return Setting_Data.size();
    }

    void addItem(SettingData data) {
        Setting_Data.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemViewHolder(View itemView, final OnListClickListener listener) {
            super(itemView);

            tvSettingItem = itemView.findViewById(R.id.tvSettingItem);
            imgSettingItem = itemView.findViewById(R.id.imgSettingItem);
            tvVersion = itemView.findViewById(R.id.tvVersion);
            switchBtn = itemView.findViewById(R.id.switchBtn);

            tvSettingItem.setOnClickListener(view -> {
                AlertDialog.Builder alter = new AlertDialog.Builder(context);
                int pos = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ItemViewHolder.this, view, pos);
                }
                for (int j = 0; j<7; j++) {
                    alter.setTitle(alertTitle.get(j));
                    alter.setMessage(alertMessage.get(j));
                    alter.setNegativeButton(alertBtnCheck.get(j), (dialogInterface, i) -> Log.d("click", "check"));

                    if (pos == j){
                        alter.create().show();
                    }
                }
                if (pos == 7){
                    //로그아웃 로직
                    Log.d("click", "로그아웃 로직 들어갈 곳");
                }
                if(pos == 8){
                    // 개인정보 수정 로직
                    Log.d("click", "개인정보 수정 로직 들어갈 곳");
                }
            });
        }

        void onBind(SettingData data) {
            tvSettingItem.setText(data.getMenu());
            imgSettingItem.setImageResource(data.getImgId());
        }

        public void setItem(SettingData item) {
            imgSettingItem.setImageResource(item.getImgId());
            tvSettingItem.setText(item.getMenu());
        }
    }
}
