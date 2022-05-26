package com.example.gramy.Community;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gramy.R;

public class NewsView extends LinearLayout {
    //뉴스 1개 보여질 xml에서 사용하는 요소
    TextView titleTV, dateTV, descTV;


    public NewsView(Context context, NewsVO data) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_fram_newsmain_custom,this,true);

        titleTV = findViewById(R.id.titleTV);
        dateTV = findViewById(R.id.dateTV);
        descTV = findViewById(R.id.descTV);

        setNews(data);


    }

    public void setNews(NewsVO data) {
        //titleTV 세팅
        if(data.getTitle()!=null && data.getTitle().trim().length()>0)
            titleTV.setText(data.getTitle().substring(0,18) + " ...");
        else
            titleTV.setText("");

        //dateTV 세팅
        if(data.getPubDate()!=null && data.getPubDate().trim().length()>0)
            dateTV.setText(data.getPubDate());
        else
            dateTV.setText("");

        //descTV 세팅
        if(data.getDescription()!=null && data.getDescription().trim().length()>0)
            descTV.setText(data.getDescription().substring(0, 31) +"  ...");
        else
            descTV.setText("");
    }
}


