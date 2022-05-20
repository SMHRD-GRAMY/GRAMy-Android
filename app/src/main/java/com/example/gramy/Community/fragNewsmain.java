package com.example.gramy.Community;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gramy.Adapter.BoardAdapter;
import com.example.gramy.BoardDetailActivity;
import com.example.gramy.BoardWriteActivity;
import com.example.gramy.Listener.OnBoardItemClickListener;
import com.example.gramy.R;
import com.example.gramy.Vo_Info.BoardVO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class fragNewsmain extends Fragment {


    FrameLayout FrameNews;
    private ListView listView;
    RequestQueue queue;
    BoardAdapter adapter = new BoardAdapter();
    ArrayList<BoardVO> items = new ArrayList<BoardVO>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_newsmain, container, false);

        FrameNews = view.findViewById(R.id.FrameNews);

        listView = view.findViewById(R.id.NewsList);
        RecyclerView recyclerView = view.findViewById(R.id.boardRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        adapter.setOnItemClickListener(new OnBoardItemClickListener() { // 인터페이스
            // TODO: 게시글 상세보기 구현
            @Override
            public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position) {
                BoardVO item = adapter.getItem(position); // 각각의 게시글
                int BoardSeq = item.getTb_a_seq(); // 게시글 번호로 조회하기
                String writerId = item.getUser_id();
                System.out.println(BoardSeq);

                // 액티비티 이동
                Intent intent = new Intent(getActivity(), BoardDetailActivity.class);
                intent.putExtra("tb_a_seq", BoardSeq);
                intent.putExtra("user_id", writerId);
                startActivity(intent);
                //
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
                startActivity(intent);
            }
        });
        queue = Volley.newRequestQueue(view.getContext());
        getBoardData();

        new NewsTask().execute();

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getBoardData() {
        int method = Request.Method.GET;
        String server_url = "http://211.48.228.51:8082/app/list";
        StringRequest request = new StringRequest(method, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(items.size() == 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject listItem = jsonArray.getJSONObject(i);
                            int tb_a_seq = listItem.getInt("tb_a_seq");
                            String tb_a_title = listItem.getString("tb_a_title");
                            String tb_a_content = listItem.getString("tb_a_content");
                            String tb_a_date = listItem.getString("tb_a_date");
                            String user_id = listItem.getString("user_id");
                            String user_name = listItem.getString("user_name");
                            BoardVO item = new BoardVO(tb_a_seq, tb_a_title, tb_a_content, tb_a_date, user_id, user_name);
                            items.add(item);
                        }
                        adapter.setItems(items);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
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