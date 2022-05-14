package com.example.gramy.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.GoReportActivity;
import com.example.gramy.HomeActivity;
import com.example.gramy.LoginActivity;
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
            fragModify = new fragModify();

            tvSettingItem.setOnClickListener(view -> {
                AlertDialog.Builder alter = new AlertDialog.Builder(context);
                int pos = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ItemViewHolder.this, view, pos);
                }
                for (int j = 0; j<7; j++) {
                    alter.setTitle(alertTitle.get(j));
                    alter.setMessage(alertMessage.get(j));
                    alter.setNegativeButton("확인", (dialogInterface, i) -> Log.d("click", "check"));

                    if (pos == 1){
                        Log.d("click", "alertDialog 안뜨게");
                    } else if(pos == 3){
                        Log.d("click", "alertDialog 안뜨게");
                    } else if(pos == 4){
                        Log.d("click", "alertDialog 안뜨게");
                    } else if(pos == 5){
                        Log.d("click", "alertDialog 안뜨게");
                    }else if(pos == j){
                        alter.create().show();
                    }
                }
                if (pos == 7){
                    //로그아웃 로직
                    Log.d("click", "로그아웃 로직 들어갈 곳");
                    SharedPreferences sf_login = view.getContext().getSharedPreferences("sf_login", view.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf_login.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    view.getContext().startActivity(intent);

                }
                if(pos == 8){

                    //openActivity();
                    openModify();
                    // 개인정보 수정 로직


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

        public void openModify(){
            FragmentManager fm = ((HomeActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            fragModify fragModify = new fragModify();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragModify);
            fragmentTransaction.commit();
        }

/*        public void openActivity(){
            if
            ((HomeActivity)HomeActivity.context_home).tvTitleGramy.setText("개인정보수정");
            else
        }*/
    }
}
