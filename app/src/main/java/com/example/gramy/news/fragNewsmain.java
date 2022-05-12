package com.example.gramy.news;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gramy.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class fragNewsmain extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private ListView listView;

    public fragNewsmain() {
        // Required empty public constructor
    }


    public static fragNewsmain newInstance(String param1, String param2) {
        fragNewsmain fragment = new fragNewsmain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_newsmain, container, false);

        listView = view.findViewById(R.id.NewsList);

        new NewsTask().execute();

        return view;
    }

    class NewsTask extends AsyncTask<Void, Void, Void> {
        List<NewsVO> newsList;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup.connect("https://fs.jtbc.joins.com//RSS/newsflash.xml").get();
                Elements elements = document.select("item");
                for (Element element : elements) {
                    NewsVO data = new NewsVO();

                    Elements e = element.select("title");
                    if (e != null && e.size() > 0)
                        data.setTitle(e.get(0).text());

                    e = element.select("link");
                    if (e != null && e.size() > 0)
                        data.setLink(e.get(0).text());

                    e = element.select("description");
                    if (e != null && e.size() > 0)
                        data.setDescription(e.get(0).text());

                    e = element.select("pubDate");
                    if (e != null && e.size() > 0)
                        data.setPubDate(e.get(0).text());

                    newsList.add(data);
                }
                Log.d("뉴스 다 읽었는지 찍어볼게요", newsList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newsList = new ArrayList<>();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            NewsAdapter adapter = new NewsAdapter(getContext(), newsList);
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