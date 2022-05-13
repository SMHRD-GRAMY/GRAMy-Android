package com.example.gramy.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.HomeActivity;
import com.example.gramy.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ItemViewHolder> implements OnListClickListener{

    private Context context;
    private TextView tvSettingItem, tvVersion;
    private ImageView imgSettingItem;
    private ArrayList<SettingData> Setting_Data;
    fragModify fragModify;

    List<String> alertTitle = Arrays.asList("공지사항","버전정보", "GRAMy소식","가게추가", "알림수신동의", "실험실", "고객센터");
    List<String> alertMessage = Arrays.asList("문의 사항이 있다면 언제든지 고객센터로 연락 주세요. 빠르게 도와드리겠습니다.","6.0.7",
            "GRAMy는 김다빈, 김민석, 김정준, 박종재, 조영혜가 모여 개발한 재고관리 서비스입니다. ","가게추가", "알림수신동의", "실험실",
            "1588-0607로 전화 또는 홈페이지의 문의게시판을 이용해주세요.");

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
            fragModify = new fragModify();

            tvSettingItem.setOnClickListener(view -> {
                int j = 0;
                AlertDialog.Builder alter = new AlertDialog.Builder(context);
                int pos = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ItemViewHolder.this, view, pos);
                }
                if (pos == 0 || pos == 1 || pos == 2 || pos == 6) {
                    alter.setTitle(alertTitle.get(j));
                    alter.setMessage(alertMessage.get(j));
                    alter.setNegativeButton("확인", (dialogInterface, i) -> Log.d("click", "check"));
                    alter.create().show();
                }
                else if(pos == 3){
                    // 가게 추가 화면으로 이동
                    GoAddStore();
                }
                else if (pos == 4){
                    // 알림수신동의
                    GoPushAgreement();
                }
                else if(pos == 5){
                    // 실험실
                    GoBetaService();
                }
                else if (pos == 7){
                    //로그아웃 로직
                }
                else if(pos == 8){
                    // 개인정보 수정 로직
                    openModify();
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

        public void GoAddStore() {
            FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            AddStoreFragment addStoreFragment = new AddStoreFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, addStoreFragment);
            fragmentTransaction.commit();
        }

        public void GoPushAgreement() {
            FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            PushAgreementFragment pushAgreementFragment = new PushAgreementFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, pushAgreementFragment);
            fragmentTransaction.commit();
        }

        public void GoBetaService(){
            FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            BetaServiceFragment betaServiceFragment = new BetaServiceFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, betaServiceFragment);
            fragmentTransaction.commit();
        }

        public void openModify(){
            FragmentManager fm = ((HomeActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            fragModify fragModify = new fragModify();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragModify);
            fragmentTransaction.commit();
        }
    }
}
