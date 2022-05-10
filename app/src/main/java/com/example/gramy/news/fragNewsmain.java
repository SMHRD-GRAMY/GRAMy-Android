package com.example.gramy.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gramy.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class fragNewsmain extends Fragment {

    private ListView listView;
    String rssURL = "https://fs.jtbc.joins.com//RSS/newsflash.xml";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_frag_newsmain, container, false);


        listView = rootView.findViewById(R.id.NewsList);



        NewsTask task=new NewsTask();
        task.execute(rssURL);

        return rootView;
    }

    class NewsTask extends AsyncTask<String,Void,Void>{


        List<NewsVO> newsList;

        //실제작업
        @Override
        protected Void doInBackground(String... params) {


            try {
                Document document = Jsoup.connect(rssURL).get();
                Elements elements = document.select("item");
                Log.d("뉴스 다 읽었는지 찍어볼게요",elements.toString());
                for (Element element : elements) {
                    NewsVO data = new NewsVO();

                    Elements e = element.select("title");
                    if (e != null && e.size()>0)
                        data.setTitle(e.get(0).text());

                    e = element.select("link");
                    if (e != null && e.size()>0)
                        data.setLink(e.get(0).text());

                    e = element.select("description");
                    if (e != null && e.size()>0)
                        data.setDescription(e.get(0).text());

                    e = element.select("pubDate");
                    if (e != null && e.size()>0)
                        data.setPubDate(e.get(0).text());

                    newsList.add(data);
                }
                Log.d("뉴스 다 읽었는지 찍어볼게요",newsList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //가장 먼저실행
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newsList = new ArrayList<>();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            NewsAdapter adapter = new NewsAdapter(getContext(),newsList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsList.get(position).getLink()));
                    startActivity(intent);
                }
            });
        }


    }



}