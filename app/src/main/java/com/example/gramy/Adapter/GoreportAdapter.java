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

import com.example.gramy.Listener.OnReportButtonClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.GoreportVO;

import org.w3c.dom.Text;

import java.util.ArrayList;



    public class GoreportAdapter extends RecyclerView.Adapter<GoreportAdapter.ViewHolder> implements OnReportButtonClickListener {
        ArrayList<GoreportVO> items = new ArrayList<GoreportVO>();
        OnReportButtonClickListener listener;


        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvShelfName;
            Button btnShelfEnroll;

            public ViewHolder(@NonNull View itemView, OnReportButtonClickListener listener) {
                super(itemView);

                tvShelfName = itemView.findViewById(R.id.tvShelfName);
                btnShelfEnroll = itemView.findViewById(R.id.btnShelfEnroll);

                btnShelfEnroll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();

                        if (listener != null) {
                            listener.onButtonClick(ViewHolder.this, view, position);
                        }
                    }
                });
            }

            public void setItem(GoreportVO item) { // setItem Method
                tvShelfName.setText(item.getShelf_name());
            }
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View itemView = inflater.inflate(R.layout.list_item2, viewGroup, false);
            return new ViewHolder(itemView, this);
        }

        @Override
        public void onBindViewHolder(@NonNull GoreportAdapter.ViewHolder holder, int position) {
            GoreportVO item = items.get(position);
            holder.setItem(item); // 뷰 홀더에 셋팅
        }

        @Override
        public int getItemCount() {
            return items.size();
        }


        ////////

        public void setOnItemClickListener(OnReportButtonClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onButtonClick(GoreportAdapter.ViewHolder holder, View view, int position) {
            if (listener != null) {
                listener.onButtonClick(holder, view, position);
            }
        }

        public void addItem(GoreportVO item) {
            items.add(item);
        }

        public void removeItem(GoreportVO item) {
        }

        public void setItems(ArrayList<GoreportVO> items) {
            this.items = items;
        }

        public GoreportVO getItem(int position) {
            return items.get(position);
        }

        public void setItem(int position, GoreportVO item) {
            items.set(position, item);
        }
    }




