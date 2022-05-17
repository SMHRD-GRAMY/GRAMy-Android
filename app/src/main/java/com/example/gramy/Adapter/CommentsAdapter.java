package com.example.gramy.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramy.Listener.OnCommentsDeleteListener;
import com.example.gramy.Listener.OnCommentsItemClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.BoardVO;
import com.example.gramy.Vo_Info.CommentVO;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> implements OnCommentsItemClickListener, OnCommentsDeleteListener {
    ArrayList<CommentVO> items = new ArrayList<CommentVO>();
    OnCommentsItemClickListener listener;
    OnCommentsDeleteListener deleteListener;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvCommentWriter;
        TextView tvComment;
        TextView tvCommentDate;
        TextView tvDeleteComment;
        TextView tvCommentWriterId;

        public ViewHolder(@NonNull ArrayList<CommentVO> items,  Context context, View itemView, OnCommentsItemClickListener listener, OnCommentsDeleteListener deleteListener) {
            super(itemView);

            tvCommentWriterId = itemView.findViewById(R.id.tvCommentWriterId);
            tvCommentWriter = itemView.findViewById(R.id.tvCommentWriter);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCommentDate = itemView.findViewById(R.id.tvCommentDate);
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

        public void setItem(CommentVO item, Context context) { // setItem Method
            SharedPreferences sharedPreferences = context.getSharedPreferences("sf_login", Context.MODE_PRIVATE);
            String writerId = sharedPreferences.getString("user_id",""); // 현재 로그인 된 유저 정보
            if(writerId.equals(item.getUser_id())) {
                    tvDeleteComment.setVisibility(View.VISIBLE);
                } else {
                    tvDeleteComment.setVisibility(View.INVISIBLE);
                }
            tvCommentWriterId.setText(item.getUser_id());
            tvCommentWriter.setText("작성자 : " + item.getUser_name());
            tvComment.setText(item.getAr_content());
            tvCommentDate.setText(item.getAr_date());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.comments_item, viewGroup, false);
        context = viewGroup.getContext();
        return new CommentsAdapter.ViewHolder(items, context, itemView, this, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        CommentVO item = items.get(position);
        holder.setItem(item, context);
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

    public void addItem(CommentVO item) {
        items.add(item);
    }

    public void removeItem(BoardVO item) {
    }

    public Context getContext (ViewGroup viewGroup) {
        return viewGroup.getContext();
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

