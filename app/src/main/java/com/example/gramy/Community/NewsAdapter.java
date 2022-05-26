package com.example.gramy.Community;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsVO> list;

    public NewsAdapter(Context context, List<NewsVO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsVO data = list.get(position);
        NewsView view = null;

        //이전에 만들어 놓은 View가 없으면
        if (convertView == null) {
            //NewsView모양으로 새로 만들고
            view = new NewsView(context, data);

            //이전에 만들어 놓은 View가 있으면
        } else {
            //만들어놨던 View(convertView)를 재활용해서 내용만 바꾼다
            view = (NewsView) convertView;
            view.setNews(data);
        }
        return view;
    }
}

