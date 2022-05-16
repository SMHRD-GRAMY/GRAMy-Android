package com.example.gramy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.Listener.OnBoardItemClickListener;
import com.example.gramy.Listener.OnCommentsDeleteListener;
import com.example.gramy.Listener.OnCommentsEditListener;
import com.example.gramy.Listener.OnCommentsItemClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.BoardVO;
import com.example.gramy.Vo_Info.CommentVO;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> implements OnCommentsItemClickListener, OnCommentsDeleteListener, OnCommentsEditListener {

    ArrayList<CommentVO> items = new ArrayList<CommentVO>();
    OnCommentsItemClickListener listener;
    OnCommentsDeleteListener deleteListener;
    OnCommentsEditListener editListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommentWriter;
        TextView tvComment;
        TextView tvCommentDate;
        TextView tvEditComment;
        TextView tvDeleteComment;

        public ViewHolder(@NonNull View itemView, OnCommentsItemClickListener listener, OnCommentsDeleteListener deleteListener, OnCommentsEditListener editListener) {
            super(itemView);

            tvCommentWriter = itemView.findViewById(R.id.tvCommentWriter);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCommentDate = itemView.findViewById(R.id.tvCommentDate);
            tvEditComment = itemView.findViewById(R.id.tvEditComment);
            tvDeleteComment = itemView.findViewById(R.id.tvDeleteComment);

            tvDeleteComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (deleteListener != null) {
                        deleteListener.onItemDelete(ViewHolder.this, view, position);
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
        return new CommentsAdapter.ViewHolder(itemView, this, this, this);
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

    public void OnCommentsItemClickListener (OnCommentsItemClickListener listener) {
        this.listener = listener;
    }

    public  void OnCommentsDeleteListener(OnCommentsDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public  void OnCommentsEditListener(OnCommentsEditListener editListener) {
        this.editListener = editListener;
    }


    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public void onItemDelete(ViewHolder holder, View view, int position) {
        if(deleteListener != null) {
            deleteListener.onItemDelete(holder, view, position);
        }
    }

    @Override
    public void onEditItem(ViewHolder holder, View view, int position) {
        if(editListener != null) {
            editListener.onEditItem(holder, view, position);
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

