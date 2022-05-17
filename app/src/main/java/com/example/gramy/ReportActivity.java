package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gramy.Adapter.GomyshelfAdapter;

import java.util.ArrayList;

import GoMgSelf.CustomAdapter;
import GoMgSelf.Dictionary;

public class ReportActivity extends AppCompatActivity {

    private ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;
    private int count = -1;
    com.example.gramy.home.fragHomemain fragHomemain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_report);

        RecyclerView recyclerView =findViewById(R.id.shelfRecyclerView);

        LinearLayoutManager LayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        GomyshelfAdapter adapter = new GomyshelfAdapter();

//        adapter.addItem(new GoreportVO("2선반","123","123"));

        recyclerView.setAdapter(adapter);


        Button buttonInsert = (Button) findViewById(R.id.btnreport);


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });

    }
}