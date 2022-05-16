package com.example.gramy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.Listener.OnBoardItemClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.BoardVO;


import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> implements OnBoardItemClickListener{
    ArrayList<BoardVO> items = new ArrayList<BoardVO>();
    OnBoardItemClickListener listener;

    // 뷰 홀더
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 메소드는 뷰 홀더 안에서 만들자!
        TextView postTitle;
        TextView postWriter;
        TextView postDate;

        public ViewHolder(@NonNull View itemView, OnBoardItemClickListener listener) {
            // 뷰 홀더 생성자
            super(itemView);

            postTitle = itemView.findViewById(R.id.postTitle);
            postWriter = itemView.findViewById(R.id.postWriter);
            postDate = itemView.findViewById(R.id.postDate);

            itemView.setOnClickListener(new View.OnClickListener() { // 아이템뷰에 클릭이벤트 달아주기
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position); // 아이템 뷰 클릭시 미리 정의한 다른 리스너의 메서드 호출
                    }
                }
            });
        }
        public void setItem(BoardVO item) { // setItem Method
            postTitle.setText(item.getTb_a_title());
            postWriter.setText(item.getUser_name());
            postDate.setText(item.getTb_a_date());
        }

    } // 뷰 홀더 end



    // 어댑터에서 구현되어야 할 메서드
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // 뷰 홀더 객체가 만들어질 때 자동으로 호출
        // 아이템을 위해 정의한 XML 레이아웃을 이용해 뷰 객체를 생성해야함
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.board_item, viewGroup, false); // 인플레이션을 통해 뷰 객체 만들기
        // board_item.xml 파일을 가지고 view 객체로 생성해줌

        return new ViewHolder(itemView, this); // 해당 뷰 홀더에 리스너를 달아주는거
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰 홀더 객체가 재사용 될 때 자동으로 호출
        // 뷰 객체는 기존 것을 그대로 사용하고 데이터만 바꿔줌
        BoardVO item = items.get(position); // position ==> 인덱스랑 비슷한 개념으로 이해

        holder.setItem(item); // 뷰 홀더에 셋팅
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    ////////////

    public void setOnItemClickListener(OnBoardItemClickListener listener) {
        this.listener = listener;
    }

    @Override // 얘도 오버라이드 하는 이유, BoardAdapter는 OnBoardItemClickListener를 implements 한다.
    public void onItemClick(ViewHolder holder, View view, int position) {
        // 외부에서 로직을 재정의하여 사용한다.
        // 이녀석의 용도는 뭐냐? Adapter상의 holder, view, position을 외부에서 쓰게끔 해주는고임
        if(listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public void addItem(BoardVO item) {
        // TODO: 게시글 추가 로직 구현하기
        items.add(item);
    }

    public void removeItem(BoardVO item) {
        // TODO: 게시물 삭제 로직 구현하기
    }

    public void setItems(ArrayList<BoardVO> items) {
        this.items = items; // 리스트 셋팅
    }

    public BoardVO getItem(int position) {
        return items.get(position); // 아이템 꺼내오기
    }

    public void setItem(int position, BoardVO item) {
        items.set(position, item); // 리스트에 아이템 셋팅 (포지션 정해서)
    }

}
