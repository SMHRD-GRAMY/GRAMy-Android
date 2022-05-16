package com.example.gramy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.Listener.OnBoardItemClickListener;
import com.example.gramy.Listener.OnCommentsItemClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.BoardVO;
import com.example.gramy.Vo_Info.CommentVO;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> implements OnCommentsItemClickListener {

    ArrayList<CommentVO> items = new ArrayList<CommentVO>();
    OnCommentsItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommentWriter;
        TextView tvComment;
        TextView tvCommentDate;

        public ViewHolder(@NonNull View itemView, OnCommentsItemClickListener listener) {
            super(itemView);

            tvCommentWriter = itemView.findViewById(R.id.tvCommentWriter);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCommentDate = itemView.findViewById(R.id.tvCommentDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(CommentsAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }
        public void setItem(CommentVO item) { // setItem Method
            tvCommentWriter.setText("작성자 : "+item.getUser_name());
            tvComment.setText(item.getAr_content());
            tvCommentDate.setText(item.getAr_date());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.comments_item, viewGroup, false);
        return new CommentsAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentVO item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    ////// 절취선 //////

    public void setOnItemClickListener(OnCommentsItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public void addItem(CommentVO item) {
        items.add(item);
    }

    public void removeItem(BoardVO item) {
    }

    public void setItems(ArrayList<CommentVO> items) {
        this.items = items;
    }

    public CommentVO getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, CommentVO item) {
        items.set(position, item);
    }
}

