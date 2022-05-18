package com.example.gramy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.Listener.OnShelfButtonClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.ShelfVO;

import java.util.ArrayList;

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ViewHolder> implements OnShelfButtonClickListener {
    ArrayList<ShelfVO> items = new ArrayList<ShelfVO>();
    OnShelfButtonClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView shelfListName;
        Button shelfDelete;
        public ViewHolder(@NonNull View itemView,OnShelfButtonClickListener listener) {
            super(itemView);

            shelfListName=itemView.findViewById(R.id.shelfListName);
            shelfDelete=itemView.findViewById(R.id.shelfDelete);

            shelfDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onButtonClick(ViewHolder.this, view, position);
                    }
                }
            });
        }
        public void setItem(ShelfVO item) { // setItem Method
            shelfListName.setText(item.getShelf_name());
        }
    }
    @NonNull
    @Override
    public ShelfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_item2, viewGroup, false);
        return new ShelfAdapter.ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShelfVO item = items.get(position);
        holder.setItem(item); // 뷰 홀더에 셋팅
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    ////////

    public void setOnItemClickListener(OnShelfButtonClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onButtonClick(ShelfAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onButtonClick(holder, view, position);
        }
    }

    public void addItem(ShelfVO item) {
        items.add(item);
    }

    public void removeItem(ShelfVO item) {
    }

    public void setItems(ArrayList<ShelfVO> items) {
        this.items = items;
    }

    public ShelfVO getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ShelfVO item) {
        items.set(position, item);
    }
}
