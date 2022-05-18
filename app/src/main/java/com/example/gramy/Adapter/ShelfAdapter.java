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
        Button shelfSelect;
        public ViewHolder(@NonNull View itemView,OnShelfButtonClickListener listener) {
            super(itemView);

            shelfListName=itemView.findViewById(R.id.shelfListName);
            shelfSelect=itemView.findViewById(R.id.shelfSelect);

            shelfSelect.setOnClickListener(new View.OnClickListener() {
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
    }// 뷰 홀더 end

    //어댑터에서 구현되어야 할 메서드
    @NonNull
    @Override
    public ShelfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //뷰 홀더 객체가 만들어질때 자동으로 호출
        // 아이템을 위해 정의한 XML 레이아웃을 이용해 뷰 객체를 생성해야함
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //xml 파일을 가지고 view 객체로 생성
        View itemView = inflater.inflate(R.layout.shelf_item, viewGroup, false);
        //해당 뷰 홀더에 리스너를 달아주는거
        return new ShelfAdapter.ViewHolder(itemView,this);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //뷰 홀더 객체가 재사용 될때 자동으로 호출
        //뷰 객체는 기존 것을 그대로 사용하고 데이터만 바꿔줌
        ShelfVO item = items.get(position);//position 인덱스랑 비슷한 개념
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

    @Override // 얘도 오버라이드 하는 이유, ShelfAdapter는 OnShelfButtonClickListener를 implements 한다.
    public void onButtonClick(ShelfAdapter.ViewHolder holder, View view, int position) {
        //외부에서 로직을 재정의하여 사용한다
        // 어탭터상의 홀더 뷰 포지션을 외부에서 쓰게 해주는거
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
