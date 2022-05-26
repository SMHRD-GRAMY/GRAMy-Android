package com.example.gramy.Adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gramy.R;
import com.example.gramy.Vo_Info.GomyshelfVO;
import com.example.gramy.Vo_Info.GoreportVO;

import java.util.ArrayList;


public class GomyshelfAdapter extends RecyclerView.Adapter<GomyshelfAdapter.ViewHolder> {
    ArrayList<GomyshelfVO> items = new ArrayList<>();

    //
    static  class ViewHolder extends  RecyclerView.ViewHolder {
        TextView name_listitem;

        public  ViewHolder(View itemView) {
            super(itemView);

        name_listitem = itemView.findViewById(R.id.name_listitem);
        }
        @RequiresApi(api = Build.VERSION_CODES.P)
        public  void setItem(GomyshelfVO item) {
            name_listitem.setText(item.getName());
        }
    }
    //
    @NonNull
    @Override
    public GomyshelfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull GomyshelfAdapter.ViewHolder viewHolder, int position) {
        GomyshelfVO item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(GomyshelfVO item) { items.add(item); }
    public void setItems(ArrayList<GomyshelfVO> items) {
        this.items = items;
    }
    public GomyshelfVO getItem(int postion) {
        return  items.get(postion);
    }
    public void setItem(int position, GomyshelfVO item) {
        items.set(position, item);
    }
}


