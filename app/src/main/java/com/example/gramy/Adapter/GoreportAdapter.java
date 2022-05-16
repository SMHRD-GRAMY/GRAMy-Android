package com.example.gramy.Adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.R;
import com.example.gramy.Vo_Info.GomyshelfVO;
import com.example.gramy.Vo_Info.GoreportVO;

import java.util.ArrayList;



    public class GoreportAdapter extends RecyclerView.Adapter<com.example.gramy.Adapter.GoreportAdapter.ViewHolder> {
        ArrayList<GoreportVO> items = new ArrayList<>();

        //
        static  class ViewHolder extends  RecyclerView.ViewHolder {
            TextView name_listitem2;
            Button btn_listitem2;

            public  ViewHolder(View itemView) {
                super(itemView);

                name_listitem2 = itemView.findViewById(R.id.id_listitem2);
                btn_listitem2 = itemView.findViewById(R.id.list_btn2);
            }
            @RequiresApi(api = Build.VERSION_CODES.P)
            public  void setItem(GoreportVO item) {
                name_listitem2.setText(item.getName());
                btn_listitem2.setText(item.getName());
            }

        }
        //
        @NonNull
        @Override
        public com.example.gramy.Adapter.GoreportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View itemView = inflater.inflate(R.layout.list_item2, viewGroup, false);
            return new com.example.gramy.Adapter.GoreportAdapter.ViewHolder(itemView);
        }
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onBindViewHolder(@NonNull GoreportAdapter.ViewHolder viewHolder, int position) {
            GoreportVO item = items.get(position);
            viewHolder.setItem(item);
        }



        @Override
        public int getItemCount() { return items.size(); }

        public void addItem(GoreportVO item) { items.add(item); }
        public void setItems(ArrayList<GoreportVO> items) { this.items = items; }
        public GoreportVO getItem(int postion) { return  items.get(postion); }
        public void setItem(int position, GoreportVO item) { items.set(position, item); }
    }




